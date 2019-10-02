package konkon.service.impl;

import konkon.model.Product;
import konkon.model.ProductForm;
import konkon.repository.ProductRepository;
import konkon.repository.impl.ProductRepositoryImpl;
import konkon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@PropertySource("classpath:global_config_app.properties")
public class ProductServiceImpl implements ProductService {
  @Autowired
  Environment env;
  @Autowired
  ProductRepository productRepository;
  @Override
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @Override
  public Product findById(Long id) {
    return productRepository.findById(id);
  }

  @Override
  public List<Product> findByName(String name) {
    return productRepository.findByName(name);
  }
  @Override
  public void delete(Long id) {
    productRepository.delete(id);
  }

  @Override
  public void add(Product product) {
    productRepository.add(product);
  }

  @Override
  public void update(Product product) {
    productRepository.update(product);
  }


  public String uploadFile(ProductForm product, BindingResult result) {
    // thong bao neu xay ra loi
    if (result.hasErrors()) {
      System.out.println("Result Error Occured" + result.getAllErrors());
    }

    // lay ten file
    MultipartFile multipartFile = product.getImage();
    String fileName = multipartFile.getOriginalFilename();
    String fileUpload = env.getProperty("file_upload");

    // luu file len server
    try {
      //multipartFile.transferTo(imageFile);

      FileCopyUtils.copy(product.getImage().getBytes(), new File(fileUpload + fileName));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return fileName;
  }

}
