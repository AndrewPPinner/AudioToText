const app = Vue.createApp ({
    data() {
        return {
        winningWord: null,
        winningCount: null,
        isLoading: false,
        word: null,
        wordPlaced: null,
        wordOfTheDay: "",
        bettingDate: null
        }
    },
    methods: {
           getUpdate() {
           this.isLoading = true;
                axios
                .get("https://andrew-pinner.asuscomm.com/audio_text/admin_page/update")
                .then(res => (this.winningWord = res.data.word, this.winningCount = res.data.count, this.isLoading = false))
                .catch(e => (console.log(e)))
           },
           setWord(word) {
           const wordMap = {"word": word}
            axios
            .post("https://andrew-pinner.asuscomm.com/audio_text/admin_page/set_daily_word", wordMap)
            .then(res => (this.wordPlaced = true, this.wordOfTheDay= word))
            .catch(e => (console.log(e), this.wordPlaced = false))
           }
       },
       mounted() {
       axios
       .get("https://andrew-pinner.asuscomm.com/audio_text/word_of_the_day")
       .then(res =>(this.wordOfTheDay = res.data))
       .catch(e => (console.log(e)));
       axios
       .get("https://andrew-pinner.asuscomm.com/audio_text/betting_day")
       .then(res => (this.bettingDate = res.data, console.log(res)))
       .catch(e =>(console.log(e)))
       }
})

app.mount("#app")