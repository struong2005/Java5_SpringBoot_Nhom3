// THÊM VÀO GIỎ
window.addToCart = function (productId) {
    fetch("/cart/add/" + productId, {
        method: "POST"
    })
    .then(res => {
        if (res.ok) {
            alert("Đã thêm vào giỏ hàng 🛒");
            updateCartCount();
        } else {
            alert("Lỗi thêm giỏ hàng");
        }
    });
};

// MUA NGAY
window.buyNow = function (productId) {
    fetch("/cart/add/" + productId, {
        method: "POST"
    })
    .then(() => {
        window.location.href = "/cart";
    });
};

// CẬP NHẬT ICON GIỎ
function updateCartCount() {
    fetch("/cart/count")
        .then(res => res.text())
        .then(count => {
            const badge = document.getElementById("cart-count");
            if (badge) badge.innerText = count;
        });
}
