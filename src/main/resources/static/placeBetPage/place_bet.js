const app = Vue.createApp ({
    data() {
        return {
        fullName: "",
        bet: null,
        wordOfTheDay: null,
        bettingDate: null,
        placed: null
        }
    },
    methods: {
        placeBet(fullName, bet) {
            const postArray = [fullName, bet]
            axios.post("https://andrew-pinner.asuscomm.com/audio_text/daily_bet", postArray)
            .then(res => (this.placed = res.data))
            .catch(e => (console.log(e)))

        }
    },
    mounted() {
    axios
    .get("https://andrew-pinner.asuscomm.com/audio_text/word_of_the_day")
    .then(res => (this.wordOfTheDay = res.data, console.log(res)))
    .catch(e =>(console.log(e)));
    axios
    .get("https://andrew-pinner.asuscomm.com/audio_text/betting_day")
    .then(res => (this.bettingDate = res.data, console.log(res)))
    .catch(e =>(console.log(e)))
    
    
    }
    
    
})

app.mount("#app")
