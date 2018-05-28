const m = require("mithril");
const Login = require("./views/Login");
const Register = require("./views/Register");
const Home = require("./views/Home");

m.route(document.body, "/home", {
  "/home": Home,
  "/register": Register,
  "/login": Login
});
