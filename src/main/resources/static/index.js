var winningCount;
const app = Vue.createApp ({
    data() {
        return {
        winners: "",
        previous_word_of_the_day: "",
        allBets: ""
        }
    },
    mounted() {
        axios("https://andrew-pinner.asuscomm.com/audio_text/daily_winner?id=123456789")
            .then(res => {
            if(res.data[0].name != null){
            this.winners = res.data;
            winningCount = res.data[0].winningBet;
            if(winningCount != -99) {
                getAllBets();
            }
            console.log(winningCount)
            $("#winning-count").text("The correct count was " + winningCount);
            }
            winningCount = res.data[0].winningBet;
            console.log(winningCount)
            $("#winning-count").text("The correct count was " + winningCount);
            })
            .catch(e => (console.log(e)));
        axios("https://andrew-pinner.asuscomm.com/audio_text/previous_word_of_the_day")
            .then(res => (this.previous_word_of_the_day = res.data))
            .catch(e => (console.log(e)))
    },
    methods: {
            getAllBets() {
                axios("https://andrew-pinner.asuscomm.com/audio_text/all_bets")
                .then(res => (this.allBets = res.data))
                .catch(e => (console.log(e)))
            }
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