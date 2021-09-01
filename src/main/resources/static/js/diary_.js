window.onload = function () {
    var modal = document.getElementById("myModal");
    var allDiary = document.querySelectorAll(".diary");


    allDiary.forEach(diary => {
        diary.addEventListener("click", function() {
            window.location.href= "/showDailyRecord/" + this.getAttribute('data-id');
        });
    });

    var createDiaryBtn = document.getElementById("create-diary-btn");
    createDiaryBtn.addEventListener("click", function() {
        modal.style.display = "block";
    });

        
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];


    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
    }

}