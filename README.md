![](logo.png)

Gas Delivery Vlora
# GasDeliveryClient
### HCI Project

The Clients part of Gas Delivery. 

## Tools

* **Android Studio**
* **Firebase**, **SQLite**
* **Material Design**
* **Drawer.io**

## Project Structure

* **Documents**
      
     * *Needefinding.pdf*
     * *client test.pdf*
     * *worker test.pdf*
     * *HCI_Vreshtazi.pdf*

### The Activities are located at: 

    /app/src/main/java/com/example/gasdelivery/

*  *MainActivity.java* - The main page. After that the user can do to  Sign Up or Sign In. The layout is loaded from *layout/activity_main.xml*. 
*  *SignUp.java* - The page to register as a new user. The layout is loaded from *layout/activity_sign_up.xml*
*  *SignIp.java* - The page to log in. After that the user is redirected to the Home page. The layout is loaded from *layout/activity_sign_in.xml*
*  *Home.java* - The list of categories. Load the categories from the database(Firebase). The layout is loaded from *layout/activity_home.xml*. There is a navigation draw and a FButton Cart, tht redirects to the cart. The categories are loaded in an adapter from the class CategoryViewHolder, that loads the view from *layout/menu_item.xml*
* *ProductList.java* - The list of products of all Categories.The container is loaded from *layout/activity_product_list.xml*. Each product is filtered by the categoryId and are loaded in an adapter from ProductViewHolder and the layout is founded at *layout/product_item.xml*. The button Add to cart execute an SQL query, that insert the product in the database.
* *Cart.java* - The list of all products added to the cart.  This list is loaded from the local DB and not from Firebase. The view is loaded from *layout/activity_cart.xml*, the products of the cart are defined at *CartAdapter.java* with the view loaded from *layout/cart_layout.xml*. When The button Place Order is clicked is shown an Alert Dialog with the view from *layout/order_fill_time_address.xml*. After confirmed the request is uploaded in Firebase. 
* *OrderStatus.java* - A list of the orders. Each order is reppresented as a Cardview holded at *OrderViewHolder.java* and the view from *layout/order_adapter.xml* .


* **Model**:

    - *Order.java* - public class where are instantiated the proprieties of the order
    
    - *User.java* -  public class where are instantiated the proprieties of the User
      
    - *Request.java* -  public class where are instantiated the proprieties of the Request
    
    - *Category.java* -  public class where are instantiated the proprieties of the Categories
    
    - *Product.java* -  public class where are instantiated the proprieties of the Product
      
 * **ViewHolder**:

     - *CartAdapter.java* 
    
     - *CategoryViewHolder* 
     
      - *ProductViewHolder* 
      
     - *OrderViewHolder.java*  
 
  * **Common**:

      *Common.java* - When a User logs in all the actions he takes are made as a commonUser.  
      
 
 * **Database**:

      *Database.java* - The local Database used for the products added to cart. Each user has a unique cart. The products are filtered by the **UsersPhone**.  
    
 
 
  
The xml files are located at:

    GasDelivery/app/src/main/res/
  
  
the layouts are found at : 
          
     app/src/main/res/layout

the menu home drawer is found at : 
        
      app/src/main/res/layout/menu

the images and icon are at: 

     res/drawable
     
     
## Authors

 **Denis Vreshtazi**
