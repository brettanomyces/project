const m = require("mithril");
const Auth = require("./models/Auth");
const Login = require("./views/Login");
const Home = require("./views/Home");

m.route(document.body, "/home", {
    "/home": {
        onmatch: function() {
            if (Auth.authenticated) {
                return Home;
            }
            m.route.set("/login")
        }
    },
    "/login": Login
});
