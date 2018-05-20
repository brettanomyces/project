const m = require("mithril");
const Login = require("./views/Login");
const Home = require("./views/Home");

m.route(document.body, "/home", {
    "/home": {
        onmatch: function() {
                return Home;
        }
    },
    "/login": Login
});
