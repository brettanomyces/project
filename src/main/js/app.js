const m = require("mithril");
const Login = require("./views/Login");
const Register = require("./views/Register");
const Home = require("./views/Home");

const Auth = require("./models/Auth");

function authenticated(page) {
  return {
    onmatch: function () {
      if (Auth.authenticated) {
        return page;
      }
      m.route.set("/login")
    }
  }
}

m.route(document.body, Auth.authenticated ? "/home" : "/login", {
  "/home": authenticated(Home),
  "/register": Register,
  "/login": Login
});
