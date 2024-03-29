package springapp.repository;

import java.util.List;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import springapp.repository.ProductDao;
import springapp.domain.Product;


public class JdbcProductDaoTests extends AbstractTransactionalDataSourceSpringContextTests
{
    private ProductDao productDao;


    public void setProductDao(ProductDao productDao)
    {
        this.productDao = productDao;
    }
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:test-context.xml"};
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.deleteFromTables(new String[] {"products"});
        super.executeSqlScript("file:db/load_data.sql",true);

    }

    public void testGetProductList() {

        List<Product> products = productDao.getProductList();

        assertEquals("wrong number of products?", 3, products.size());

    }

    public void testSaveProduct() {

        List<Product> products = productDao.getProductList();

        for (Product p : products)
        {
            p.setPrice(200.12);
            this.productDao.saveProduct(p);
        }

        List<Product> updateProducts = this.productDao.getProductList();
        for (Product p : updateProducts) {
            assertEquals("wrong number of product?", 200.12,p.getPrice());
        }
    }
}
