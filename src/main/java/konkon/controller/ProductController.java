package konkon.controller;

import konkon.model.Product;
import konkon.model.ProductForm;
import konkon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
  @Autowired
  private ProductService productService;

  @GetMapping("/create")
  public ModelAndView showCreateForm() {
    ModelAndView modelAndView = new ModelAndView("/product/create");
    modelAndView.addObject("product", new ProductForm());
    return modelAndView;
  }

  @PostMapping("/create")
  public ModelAndView createProduct(@ModelAttribute ProductForm productform, BindingResult result) {
    String fileName = productService.uploadFile(productform, result);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
    try {
      Date lDate = simpleDateFormat.parse(productform.getCreateDate());
      Product productObject = new Product(productform.getCreateDate(), fileName, productform.getName(), productform.getPrice(), productform.getQuantity(), productform.getDescription(), productform.getActive());
      productService.add(productObject);
      ModelAndView modelAndView = new ModelAndView("redirect:/product/products");
      modelAndView.addObject("product", productObject);
      return modelAndView;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    ModelAndView modelAndView = new ModelAndView("/product/error");
    return modelAndView;
  }

  @GetMapping("/products")
  public ModelAndView showProducts() {

    List<Product> productList = productService.findAll();

    ModelAndView modelAndView = new ModelAndView("/product/products");
    modelAndView.addObject("products", productList);

    return modelAndView;
  }

  @GetMapping("/edit")
  public ModelAndView showEditForm(@ModelAttribute Product product) {
    ModelAndView modelAndView = new ModelAndView("/product/edit");
    Product productObject = productService.findById(product.getId());
    modelAndView.addObject("product", productObject);
    return modelAndView;
  }

  @PostMapping("/edit")
  public ModelAndView editProduct(@ModelAttribute ProductForm productForm, BindingResult result) {
    String fileName = productService.uploadFile(productForm, result);

    Product productObject = productService.findById(productForm.getId());
    if (!fileName.equals("")) {
      productObject.setImage(fileName);
    }
    productObject.setCreateDate(productForm.getCreateDate());
    productObject.setName(productForm.getName());
    productObject.setPrice(productForm.getPrice());
    productObject.setQuantity(productForm.getQuantity());
    productObject.setDescription(productForm.getDescription());
    productService.save(productObject);
    ModelAndView modelAndView = new ModelAndView("redirect:/product/products");
    modelAndView.addObject("product", productObject);
    return modelAndView;
  }

  @PostMapping("/search")
  public ModelAndView search(@RequestParam String name) {
    List<Product> foundProductList = productService.findByName(name);
    if (foundProductList != null) {
      ModelAndView modelAndView = new ModelAndView(("/product/search"));
      modelAndView.addObject("products", foundProductList);
      return modelAndView;
    } else {
      ModelAndView modelAndView = new ModelAndView("/product/products");
      List<Product> products = productService.findAll();
      modelAndView.addObject("products", products);
      return modelAndView;
    }
  }
  @GetMapping("/view")
  public ModelAndView showViewForm(@ModelAttribute Product product){
    ModelAndView modelAndView = new ModelAndView("/product/view");
    Product productObject = productService.findById(product.getId());
    modelAndView.addObject("product", productObject);
    return modelAndView;
  }

  @GetMapping("/delete")
  public ModelAndView showDeleteForm(@ModelAttribute Product product){
    ModelAndView modelAndView = new ModelAndView("/product/delete");
    Product productObject = productService.findById(product.getId());
    modelAndView.addObject("product", productObject);
    return modelAndView;
  }

  @PostMapping("/delete")
  public ModelAndView deleteProduct(@ModelAttribute ProductForm productForm){
    Product productObject = productService.findById(productForm.getId());
    productService.delete(productObject.getId());
    ModelAndView modelAndView = new ModelAndView("redirect:/product/products");
    modelAndView.addObject("product", productObject);
    return modelAndView;
  }
}
