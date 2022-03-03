var winningCount;
const app = Vue.createApp ({
    data() {
        return {
        winners: ""
        }
    },
    mounted() {
        axios("https://andrew-pinner.asuscomm.com/audio_text/daily_winner?id=123456789")
            .then(res => {
            if(res.data[0].name != null){
            this.winners = res.data;
            winningCount = res.data[0].winningBet;
            console.log(winningCount)
            $("#winning-count").text("The correct count was " + winningCount);
            }
            winningCount = res.data[0].winningBet;
            console.log(winningCount)
            $("#winning-count").text("The correct count was " + winningCount);
            })
            .catch(e => (console.log(e)))
    }
})

app.mount("#app")

//Show Today's winners as today's date
var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); 
var yyyy = today.getFullYear();
today = mm + '/' + dd;
console.log(today);
console.log(winningCount);
$(document).ready(function(event){ 
    $("#today-winner-title").text("Results for Java lecture on " + today + ":");
  });