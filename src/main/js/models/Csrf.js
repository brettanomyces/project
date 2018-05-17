const Csrf = {
  token: document.querySelector("meta[name=\"_csrf\"]").getAttribute("content"),
  header: document.querySelector("meta[name=\"_csrf_header\"]").getAttribute("content")
};

module.exports = Csrf;