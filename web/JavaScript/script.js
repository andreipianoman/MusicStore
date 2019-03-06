function displayQuantityInput() {
    var XS = document.getElementById("quantityXS");
    var S = document.getElementById("quantityS");
    var M = document.getElementById("quantityM");
    var L = document.getElementById("quantityL");
    var XL = document.getElementById("quantityXL");
    var XXL = document.getElementById("quantityXXL");
    
    if (XS !== null) {
        document.getElementById("quantityXS").classList.remove("hidden");
        console.log("fuck");
        return;
    } else if (S !== null) {
        document.getElementById("quantityS").classList.remove("hidden");
        console.log("fuck");
        return;
    } else if (M !== null) {
        document.getElementById("quantityM").classList.remove("hidden");
        console.log("fuck");
        return;
    } else if (L !== null) {
        document.getElementById("quantityL").classList.remove("hidden");
        console.log("fuck");
        return;
    } else if (XL !== null) {
        document.getElementById("quantityXL").classList.remove("hidden");
        console.log("fuck");
        return;
    } else if (XXL !== null) {
        document.getElementById("quantityXXL").classList.remove("hidden");
        console.log("fuck");
        return;
    }
}

function updateQuantityInput() {
    console.log("run");
    if (document.getElementById("quantityXS")) {
        document.getElementById("quantityXS").classList.add("hidden");
    }
    
    if (document.getElementById("quantityS")) {
        document.getElementById("quantityS").classList.add("hidden");
    }
    if (document.getElementById("quantityM")) {
        document.getElementById("quantityM").classList.add("hidden");
    }
    
    if (document.getElementById("quantityL")) {
        document.getElementById("quantityL").classList.add("hidden");
    }
    
    if (document.getElementById("quantityXL")) {
        document.getElementById("quantityXL").classList.add("hidden");
    }
    
    if (document.getElementById("quantityXXL")) {
        document.getElementById("quantityXXL").classList.add("hidden");
    }
    
    var size = document.getElementById('selectSizes').value;
    document.getElementById("quantity" + size).classList.remove("hidden");
}