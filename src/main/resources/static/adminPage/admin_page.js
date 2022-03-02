const app = Vue.createApp ({
    data() {
        return {
        winningWord: null,
        winningCount: null,
        isLoading: false,
        word: null,
        wordPlaced: null,
        wordOfTheDay: ""
        }
    },
    methods: {
           getUpdate() {
           this.isLoading = true;
                axios
                .get("http://localhost:7070/admin_page/update")
                .then(res => (this.winningWord = res.data.word, this.winningCount = res.data.count, this.isLoading = false))
                .catch(e => (console.log(e)))
           },
           setWord(word) {
           const wordMap = {"word": word}
            axios
            .post("http://localhost:7070/admin_page/set_daily_word", wordMap)
            .then(res => (this.wordPlaced = true))
            .catch(e => (console.log(e), this.wordPlaced = false))
           }
       },
       mounted() {
       axios
       .get("http://localhost:7070/word_of_the_day")
       .then(res =>(this.wordOfTheDay = res.data))
       .catch(e => (console.log(e)))
       }
})

app.mount("#app")