const app = Vue.createApp ({
    data() {
        return {
        fullName: "",
        bet: null,
        wordOfTheDay: "",
        placed: null
        }
    },
    methods: {
        placeBet(fullName, bet) {
            axios("http://localhost:7070/daily_bet?parameters=" + fullName + "," + bet)
            .then(res => (this.placed = res.data))
            .catch(e => (console.log(e)))

        }
    }
})

app.mount("#app")