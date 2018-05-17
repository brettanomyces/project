const m = require("mithril");
const Csrf = require("./Csrf");

const Auth = {
  username: "",
  password: "",
  authenticated: false,

  setUsername: function (value) {
    Auth.username = value
  },
  setPassword: function (value) {
    Auth.password = value
  },
  canSubmit: function () {
    return Auth.username !== "" && Auth.password !== ""
  },
  login: function () {
    m.request({
      method: "POST",
      url: "/api/login",
      headers: {
        [Csrf.header]: Csrf.token
      },
      data: {
        "username": Auth.username,
        "password": Auth.password,
      }
    })
      .then(function(result) {
        Auth.authenticated = true;
        m.route.set("home")
      })
      .catch(function (e) {
        console.error("catch");
      })
  },
  logout: function () {
    m.request({
      method: "POST",
      url: "/api/logout",
      headers: {
        [Csrf.header] : Csrf.token
      }
    })
      .then(function() {
        Auth.authenticated = false;
        m.route.set("/login");
      })
  }
};

module.exports = Auth;