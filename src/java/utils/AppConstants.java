package utils;

public class AppConstants {

    // --- VIETQR CONFIG ---
    public static final String VIETQR_BASE_URL = "https://img.vietqr.io/image/";
    public static final String VIETQR_BANK_ID = "MBBANK";
    public static final String VIETQR_ACCOUNT_NUMBER = "0915601343";
    public static final String VIETQR_ACCOUNT_NAME = "TRAN QUOC AN";
    public static final String VIETQR_TEMPLATE = "compact2";

    // --- VIEWS ---
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String REGISTER_PAGE = "register.jsp";
    public static final String HOME_PAGE = "home.jsp";

    public static final String CLIENT_SHOP_PAGE = "selectProduct.jsp";
    public static final String CLIENT_CART_PAGE = "viewCart.jsp";
    public static final String CLIENT_CHECKOUT_PAGE = "myCheckout.jsp";
    public static final String CLIENT_QR_PAYMENT_PAGE = "qrPayment.jsp";
    public static final String MY_ORDER_LIST_PAGE = "myOrderList.jsp";
    public static final String MY_ORDER_DETAIL_PAGE = "myOrderDetail.jsp";

    public static final String PRODUCT_LIST_PAGE = "listProduct.jsp";
    public static final String PRODUCT_CREATE_PAGE = "createProduct.jsp";
    public static final String PRODUCT_EDIT_PAGE = "editProduct.jsp";
    public static final String PRODUCT_DETAIL_PAGE = "productDetail.jsp";

    public static final String CATEGORY_LIST_PAGE = "listCategory.jsp";
    public static final String CATEGORY_CREATE_PAGE = "createCategory.jsp";
    public static final String CATEGORY_EDIT_PAGE = "editCategory.jsp";

    public static final String ORDER_LIST_PAGE = "staff_listOrder.jsp";
    public static final String ORDER_CREATE_PAGE = "staff_createOrder.jsp";
    public static final String ORDER_EDIT_PAGE = "staff_editOrder.jsp";
    public static final String ORDER_DETAIL_PAGE = "staff_detailOrder.jsp";

    public static final String ORDER_ITEM_LIST_PAGE = "listOrderItem.jsp";
    public static final String ORDER_ITEM_CREATE_PAGE = "createOrderItem.jsp";
    public static final String ORDER_ITEM_EDIT_PAGE = "editOrderItem.jsp";

    public static final String ACCOUNT_LIST_PAGE = "admin_listAccount.jsp";
    public static final String ACCOUNT_CREATE_PAGE = "admin_createAccount.jsp";
    public static final String ACCOUNT_EDIT_PAGE = "admin_editAccount.jsp";

    public static final String WISHLIST_LIST_PAGE = "listWishlist.jsp";
    public static final String WISHLIST_CREATE_PAGE = "createWishlist.jsp";
    public static final String WISHLIST_EDIT_PAGE = "editWishlist.jsp";

    public static final String REVIEW_LIST_PAGE = "listReview.jsp";
    public static final String REVIEW_CREATE_PAGE = "createReview.jsp";
    public static final String REVIEW_EDIT_PAGE = "editReview.jsp";

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
    public static final String ACTION_SHOW_PRODUCT_DETAIL = "showProductDetail";
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
    public static final String ACTION_SHOW_QR_PAYMENT = "showQrPayment";
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
    public static final String ACTION_SHOW_ACCOUNT = "showAccount";
    public static final String ACTION_UPDATE_PROFILE = "updateProfile";

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
