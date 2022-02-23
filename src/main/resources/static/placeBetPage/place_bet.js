const app = Vue.createApp ({
    data() {
        return {
        fullName: "",
        bet: null,
        wordOfTheDay: null,
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
    }
})

app.mount("#app")