setInterval(function () {
    if (document.location.href === "http://localhost:8080/prices") {
        document.location.href = "http://localhost:8080/prices";
    }
    // if (document.location.href === "http://timewars.online:3001/main") {
    //     document.location.href = "http://timewars.online:3001/main";
    // }
}, 1000);