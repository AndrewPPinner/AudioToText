const app = Vue.createApp ({
    data() {
        return {
        winners: "",
        }
    },
    mounted() {
        axios("http://localhost:7070/daily_winner?id=123456789")
            .then(res => {
            this.winners = res.data
            })
            .catch(e => (console.log(e)))
    }
})

app.mount("#app")