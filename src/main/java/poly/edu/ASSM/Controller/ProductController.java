package poly.edu.ASSM.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.edu.ASSM.Entitty.Category;
import poly.edu.ASSM.Entitty.Product;
import poly.edu.ASSM.Services.core.CategoryServiceImpl;
import poly.edu.ASSM.Services.core.ProductServiceImpl;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductServiceImpl productService;
	
	@Autowired
	CategoryServiceImpl catService;
	
	@GetMapping
    public String products(
            @RequestParam(required = false) String cat,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "price") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model
    ) {
		Sort.Direction direction = 
				dir.equalsIgnoreCase("asc")
					? Sort.Direction.ASC
				    : Sort.Direction.DESC;
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(direction, sort));
		
		Page<Product> products = productService.filterProducts(cat, keyword, min, max, pageable);
		
		List<Category> categories = catService.findAll();
		
		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
        model.addAttribute("cat", cat);
        model.addAttribute("keyword", keyword);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);

        return "page/product";
	}
	
	@GetMapping("/page")
    public String ajaxProducts(
            @RequestParam(required = false) String cat,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "price") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model
    ) {

       Pageable pageable = PageRequest.of(page, 10, 
    		   						Sort.by(dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sort));
       
       Page <Product> products = productService.filterProducts(cat, keyword, min, max, pageable);
       
       model.addAttribute("products", products);
       
       return "fragments/product-list :: productList";
    }
	@GetMapping("/{id}")
	public String productDetail(@RequestParam(required = false) String cat,
	                            @RequestParam(required = false) String keyword,
	                            @RequestParam(required = false) Double min,
	                            @RequestParam(required = false) Double max,
	                            @RequestParam(defaultValue = "0") int page,
	                            @RequestParam(defaultValue = "price") String sort,
	                            @RequestParam(defaultValue = "asc") String dir,
	                            @org.springframework.web.bind.annotation.PathVariable("id") Integer id,
	                            Model model) {

	    Product product = productService.findById(id);

	    List<Category> categories = catService.findAll();

	    model.addAttribute("product", product);
	    model.addAttribute("categories", categories);

	    return "page/product-detail";
	}
	@GetMapping("/product/{id}")
	public String detail(@PathVariable Integer id, Model model) {
	    model.addAttribute("product", productService.findById(id));
	    return "page/product-detail";
	}

}
