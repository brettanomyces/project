const m = require("mithril");

const Auth = {
  login: function (username, password) {
    return m.request({
      method: "POST",
      url: "/auth/login",
      data: {
        "username": username,
        "password": password
      }
    })
  },

  logout: function () {
    m.request({
      method: "POST",
      url: "/auth/logout",
    })
      .finally(function() {
        m.route.set("/login");
      })
  }
};

module.exports = Auth;