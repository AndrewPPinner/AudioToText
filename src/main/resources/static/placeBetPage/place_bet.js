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
            axios.post("http://localhost:7070/daily_bet", postArray)
            .then(res => (this.placed = res.data))
            .catch(e => (console.log(e)))

        }
    },
    mounted() {
    axios
    .get("http://localhost:7070/word_of_the_day")
    .then(res => (this.wordOfTheDay = res.data, console.log(res)))
    .catch(e =>(console.log(e)));
    axios
    .get("http://localhost:7070/betting_day")
    .then(res => (this.bettingDate = res.data, console.log(res)))
    .catch(e =>(console.log(e)))
    
    
    }
    
    
})

app.mount("#app")