document.querySelectorAll("form").forEach(function (form) {
  form.addEventListener("submit", function (event) {
    event.preventDefault();
    loadCSS("/LoadingAnimation/loading-animation.css");
    showLoadingAnimation();
    setTimeout(() => {
      form.submit();
    }, 1000);
  });
});

function loadCSS(url) {
  var link = document.createElement("link");
  link.rel = "stylesheet";
  link.type = "text/css";
  link.href = url;
  document.head.appendChild(link);
}

function showLoadingAnimation() {
  var overlay = document.createElement("div");
  overlay.className = "overlay";
  var loadingAnimation = document.createElement("div");
  loadingAnimation.className = "pan-loader";
  loadingAnimation.innerHTML = `
    <div class="overlay">
        <div class="main">    
            <div class="pan-loader">
                <div class="loader"></div>
                    <div class="pan-container">
                        <div class="pan"></div>
                            <div class="handle"></div>
                        </div>
                        <div class="shadow"></div>
            </div>
        </div>
    </div>
        `;
  overlay.appendChild(loadingAnimation);
  document.body.appendChild(overlay);
}
