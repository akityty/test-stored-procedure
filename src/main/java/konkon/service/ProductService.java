package konkon.service;

import konkon.model.Product;
import konkon.model.ProductForm;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface ProductService extends GeneralService<Product> {
  String uploadFile(@ModelAttribute ProductForm product, BindingResult result);
}
