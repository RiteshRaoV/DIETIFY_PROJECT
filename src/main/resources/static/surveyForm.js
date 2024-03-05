
document.getElementById('timeFrame').addEventListener('change', function() {
    var selectedValue = this.value;
    document.getElementById('msform').action = '/mealplanner/' + selectedValue;
});



document.addEventListener("DOMContentLoaded", function(){
var current_fs, next_fs, previous_fs; // fieldsets
var opacity;
var userData = {};

document.querySelectorAll(".next").forEach(function(nextBtn){
    nextBtn.addEventListener("click", function(){
        current_fs = this.parentElement;
        next_fs = this.parentElement.nextElementSibling;

        // Add Class Active
        document.querySelectorAll("#progressbar li")[Array.prototype.indexOf.call(document.querySelectorAll("fieldset"), next_fs)].classList.add("active");
        document.querySelectorAll("fieldset").forEach(function(fs){
            if (fs !== next_fs) {
                fs.style.display = "none";
            }
        });

        ///
        var inputs = next_fs.querySelectorAll("input, select");
        inputs.forEach(function(input){
            userData[input.id] = input.value;
        });
        console.log(userData);

        // Show the next fieldset
        next_fs.style.display = "block";

        // to store user nputs
        

        // Hide the current fieldset with style
        var animationInterval = setInterval(function(){
            if (opacity >= 1){
                clearInterval(animationInterval);
            }
            opacity += 0.1;
            current_fs.style.opacity = 1 - opacity;
            next_fs.style.opacity = opacity;
        }, 60);
    });
});

document.querySelectorAll(".previous").forEach(function(prevBtn){
    prevBtn.addEventListener("click", function(){
        current_fs = this.parentElement;
        previous_fs = this.parentElement.previousElementSibling;

        // Remove class active
        document.querySelectorAll("#progressbar li")[Array.prototype.indexOf.call(document.querySelectorAll("fieldset"), current_fs)].classList.remove("active");

        document.querySelectorAll("fieldset").forEach(function(fs){
            if (fs !== previous_fs) {
                fs.style.display = "none";
            }
        });

        // Show the previous fieldset
        previous_fs.style.display = "block";

        // Hide the current fieldset with style
        var animationInterval = setInterval(function(){
            if (opacity >= 1){
                clearInterval(animationInterval);
            }
            opacity += 0.1;
            current_fs.style.opacity = 1 - opacity;
            previous_fs.style.opacity = opacity;
        }, 60);
    });
});

document.querySelectorAll(".radio-group .radio").forEach(function(radio){
    radio.addEventListener("click", function(){
        this.parentElement.querySelectorAll(".radio").forEach(function(radio){
            radio.classList.remove("selected");
        });
        this.classList.add("selected");
    });
});

document.querySelectorAll(".submit").forEach(function(submitBtn){
    submitBtn.addEventListener("click", function(){
        return false;
    });
});

});