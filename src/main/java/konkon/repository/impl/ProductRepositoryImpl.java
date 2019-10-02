package konkon.repository.impl;

import konkon.model.Product;
import konkon.repository.ProductRepository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;


@Transactional
public class ProductRepositoryImpl implements ProductRepository {
  @PersistenceContext
  private EntityManager em;


  @Override
  public List<Product> findAll() {
    StoredProcedureQuery findAllProductQuery = em.createNamedStoredProcedureQuery("findAllProduct");
    List<Product> products = findAllProductQuery.getResultList();
    return products;
  }

  @Override
  public Product findById(Long id) {
    StoredProcedureQuery findByIdQuery = em.createNamedStoredProcedureQuery("findById");
    findByIdQuery.setParameter("inputId", id);
    findByIdQuery.execute();
    Product product = (Product) findByIdQuery.getSingleResult();

    return product;
  }

  @Override
  public List<Product> findByName(String name) {
    StoredProcedureQuery findByNameQuery = em.createNamedStoredProcedureQuery("findByName");
    findByNameQuery.setParameter("inputName",'%'+name+'%');
    findByNameQuery.execute();
    List<Product> products = findByNameQuery.getResultList();
    return products;
  }

  @Override
  public void save(Product product) {
    if (product.getId() != null) {
      em.merge(product);
    } else {
      add(product);
    }
  }

  @Override
  public void delete(Long id) {
    StoredProcedureQuery deleteProductQuery = em.createNamedStoredProcedureQuery("deleteProduct");
    deleteProductQuery.setParameter("inputId",id);
    deleteProductQuery.execute();
  }

  @Override
  public void add(Product product) {
    java.text.SimpleDateFormat sdf =
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String createDate = sdf.format(product.getCreateDate());

    StoredProcedureQuery spAddProduct = em.createNamedStoredProcedureQuery("addProduct");
    spAddProduct.setParameter("active", product.getActive());
    spAddProduct.setParameter("createDate", Timestamp.valueOf(createDate));
    spAddProduct.setParameter("description", product.getDescription());
    spAddProduct.setParameter("image", product.getImage());
    spAddProduct.setParameter("name", product.getName());
    spAddProduct.setParameter("price", product.getPrice());
    spAddProduct.setParameter("quantity", product.getQuantity());
    spAddProduct.execute();
  }

/*  public void update(int id, Product product) {
    products.put(id,product);
  }*/

  @Override
  public void update(Product product) {
    java.text.SimpleDateFormat sdf =
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String createDate = sdf.format(product.getCreateDate());
    StoredProcedureQuery spAddProduct = em.createNamedStoredProcedureQuery("updateProduct");
    spAddProduct.setParameter("inputId", product.getId());
    spAddProduct.setParameter("inputActive",product.getActive());
    spAddProduct.setParameter("inputCreateDate", Timestamp.valueOf(createDate));
    spAddProduct.setParameter("inputDescription", product.getDescription());
    spAddProduct.setParameter("inputImage", product.getImage());
    spAddProduct.setParameter("inputName", product.getName());
    spAddProduct.setParameter("inputPrice", product.getPrice());
    spAddProduct.setParameter("inputQuantity", product.getQuantity());
    spAddProduct.execute();
  }
}
