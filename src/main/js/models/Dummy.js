const m = require("mithril");
const Csrf = require("./Csrf");

const Dummy = {
  dummy: function () {
    m.request({
      method: "GET",
      url: "/api/dummy",
      header: {
        [Csrf.header]: Csrf.token
      }
    }).then(function (result) {
      console.log(result)
    })
  }
};

module.exports = Dummy;