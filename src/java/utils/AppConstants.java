package utils;

public class AppConstants {

    // --- VIEWS ---
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String REGISTER_PAGE = "register.jsp";
    public static final String HOME_PAGE = "views/client/home/home.jsp";
    
    public static final String CLIENT_SHOP_PAGE = "/views/client/shop/selectProduct.jsp";
    public static final String CLIENT_CART_PAGE = "/views/client/shop/cart.jsp";
    public static final String CLIENT_CHECKOUT_PAGE = "/views/client/order/checkout.jsp";

    public static final String PRODUCT_LIST_PAGE = "views/staff/product/list.jsp";
    public static final String PRODUCT_CREATE_PAGE = "views/staff/product/create.jsp";
    public static final String PRODUCT_EDIT_PAGE = "views/staff/product/edit.jsp";

    public static final String CATEGORY_LIST_PAGE = "views/staff/category/list.jsp";
    public static final String CATEGORY_CREATE_PAGE = "views/staff/category/create.jsp";
    public static final String CATEGORY_EDIT_PAGE = "views/staff/category/edit.jsp";

    public static final String ORDER_LIST_PAGE = "views/staff/order/list.jsp";
    public static final String ORDER_CREATE_PAGE = "views/staff/order/create.jsp";
    public static final String ORDER_EDIT_PAGE = "views/staff/order/edit.jsp";

    public static final String ORDER_ITEM_LIST_PAGE = "views/staff/orderitem/list.jsp";
    public static final String ORDER_ITEM_CREATE_PAGE = "views/staff/orderitem/create.jsp";
    public static final String ORDER_ITEM_EDIT_PAGE = "views/staff/orderitem/edit.jsp";
    public static final String ORDER_DETAIL_PAGE = "views/staff/order/detail.jsp";
    public static final String MY_ORDER_LIST_PAGE = "views/client/order/myOrderList.jsp";
    public static final String MY_ORDER_DETAIL_PAGE = "views/client/order/myOrderDetail.jsp";

    public static final String ACCOUNT_LIST_PAGE = "views/admin/account/list.jsp";
    public static final String ACCOUNT_CREATE_PAGE = "views/admin/account/create.jsp";
    public static final String ACCOUNT_EDIT_PAGE = "views/admin/account/edit.jsp";

    public static final String WISHLIST_LIST_PAGE = "views/client/wishlist/list.jsp";
    public static final String WISHLIST_CREATE_PAGE = "views/client/wishlist/create.jsp";
    public static final String WISHLIST_EDIT_PAGE = "views/client/wishlist/edit.jsp";

    public static final String REVIEW_LIST_PAGE = "views/client/review/list.jsp";
    public static final String REVIEW_CREATE_PAGE = "views/client/review/create.jsp";
    public static final String REVIEW_EDIT_PAGE = "views/client/review/edit.jsp";

    // --- CONTROLLERS ---
    public static final String MAIN_CONTROLLER = "MainController";
    public static final String PRODUCT_CONTROLLER = "ProductController";
    public static final String CATEGORY_CONTROLLER = "CategoryController";
    public static final String ORDER_CONTROLLER = "OrderController";
    public static final String ORDER_ITEM_CONTROLLER = "OrderItemController";
    public static final String ACCOUNT_CONTROLLER = "AccountController";
    public static final String WISHLIST_CONTROLLER = "WishlistController";
    public static final String REVIEW_CONTROLLER = "ReviewController";

    // --- SESSION ---
    public static final String SESSION_LOGIN_USER = "LOGIN_USER";
    public static final String ACTION_SHOW_HOME = "showHome";
    // --- AUTH ACTIONS ---
    public static final String ACTION_SHOW_LOGIN = "showLogin";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_LOGOUT = "logout";
    public static final String ACTION_SHOW_REGISTER = "showRegister";
    public static final String ACTION_REGISTER = "register";

    // --- PRODUCT ACTIONS ---
    public static final String ACTION_LIST_PRODUCT = "listProduct";
    public static final String ACTION_SHOW_CREATE_PRODUCT = "showCreateProduct";
    public static final String ACTION_INSERT_PRODUCT = "insertProduct";
    public static final String ACTION_DELETE_PRODUCT = "deleteProduct";
    public static final String ACTION_EDIT_PRODUCT = "editProduct";
    public static final String ACTION_UPDATE_PRODUCT = "updateProduct";
    public static final String ACTION_SHOW_SHOP = "showShop";
    public static final String ACTION_ADD_TO_CART = "addToCart";
    public static final String ACTION_SHOW_CART = "showCart";
    public static final String ACTION_INCREASE_CART_ITEM = "increaseCartItem";
    public static final String ACTION_DECREASE_CART_ITEM = "decreaseCartItem";
    public static final String ACTION_REMOVE_FROM_CART = "removeFromCart";

    // --- CATEGORY ACTIONS ---
    public static final String ACTION_LIST_CATEGORY = "listCategory";
    public static final String ACTION_SHOW_CREATE_CATEGORY = "showCreateCategory";
    public static final String ACTION_INSERT_CATEGORY = "insertCategory";
    public static final String ACTION_DELETE_CATEGORY = "deleteCategory";
    public static final String ACTION_EDIT_CATEGORY = "editCategory";
    public static final String ACTION_UPDATE_CATEGORY = "updateCategory";

    // --- ORDER ACTIONS ---
    public static final String ACTION_LIST_ORDER = "listOrder";
    public static final String ACTION_SHOW_CREATE_ORDER = "showCreateOrder";
    public static final String ACTION_INSERT_ORDER = "insertOrder";
    public static final String ACTION_DELETE_ORDER = "deleteOrder";
    public static final String ACTION_EDIT_ORDER = "editOrder";
    public static final String ACTION_UPDATE_ORDER = "updateOrder";
    public static final String ACTION_SHOW_CHECKOUT = "showCheckout";
    public static final String ACTION_PLACE_ORDER_FROM_CART = "placeOrderFromCart";
    public static final String ACTION_APPROVE_ORDER = "approveOrder";
    public static final String ACTION_CANCEL_ORDER = "cancelOrder";
    public static final String ACTION_LIST_MY_ORDER = "listMyOrder";
    public static final String ACTION_SHOW_MY_ORDER_DETAIL = "showMyOrderDetail";

    // --- ORDER ITEM ACTIONS ---
    public static final String ACTION_LIST_ORDER_ITEM = "listOrderItem";
    public static final String ACTION_SHOW_CREATE_ORDER_ITEM = "showCreateOrderItem";
    public static final String ACTION_INSERT_ORDER_ITEM = "insertOrderItem";
    public static final String ACTION_DELETE_ORDER_ITEM = "deleteOrderItem";
    public static final String ACTION_EDIT_ORDER_ITEM = "editOrderItem";
    public static final String ACTION_UPDATE_ORDER_ITEM = "updateOrderItem";
    public static final String ACTION_SHOW_ORDER_DETAIL = "showOrderDetail";

    // --- ACCOUNT ACTIONS ---
    public static final String ACTION_LIST_ACCOUNT = "listAccount";
    public static final String ACTION_SHOW_CREATE_ACCOUNT = "showCreateAccount";
    public static final String ACTION_INSERT_ACCOUNT = "insertAccount";
    public static final String ACTION_DELETE_ACCOUNT = "deleteAccount";
    public static final String ACTION_EDIT_ACCOUNT = "editAccount";
    public static final String ACTION_UPDATE_ACCOUNT = "updateAccount";

    // --- WISHLIST ACTIONS ---
    public static final String ACTION_LIST_WISHLIST = "listWishlist";
    public static final String ACTION_SHOW_CREATE_WISHLIST = "showCreateWishlist";
    public static final String ACTION_INSERT_WISHLIST = "insertWishlist";
    public static final String ACTION_DELETE_WISHLIST = "deleteWishlist";
    public static final String ACTION_EDIT_WISHLIST = "editWishlist";
    public static final String ACTION_UPDATE_WISHLIST = "updateWishlist";

    // --- REVIEW ACTIONS ---
    public static final String ACTION_LIST_REVIEW = "listReview";
    public static final String ACTION_SHOW_CREATE_REVIEW = "showCreateReview";
    public static final String ACTION_INSERT_REVIEW = "insertReview";
    public static final String ACTION_DELETE_REVIEW = "deleteReview";
    public static final String ACTION_EDIT_REVIEW = "editReview";
    public static final String ACTION_UPDATE_REVIEW = "updateReview";
}
