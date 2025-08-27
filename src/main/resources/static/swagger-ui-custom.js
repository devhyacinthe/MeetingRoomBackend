window.onload = function() {
    // Vérifie si un token est stocké dans localStorage
    const token = localStorage.getItem("swaggerToken");
    if (token) {
        const ui = window.ui; // Swagger UI instance
        ui.preauthorizeApiKey("bearerAuth", "Bearer " + token);
    }

    // Intercepte le bouton "Authorize" pour sauvegarder le token dans localStorage
    const originalAuthorize = window.ui.preauthorizeApiKey;
    window.ui.preauthorizeApiKey = function (name, value) {
        localStorage.setItem("swaggerToken", value.replace("Bearer ", ""));
        originalAuthorize(name, value);
    };
};
