<template>
    <div class="pop-up">
    <h2>{{ header }}</h2>
    <p>{{  message }}</p>
    <!-- <button @click="close">Ok</button> -->
    </div>
</template>

<script>

export default {
    name: 'LoggedInMessage',
    props: ['header', 'message'],
    data(){

    },
    // methods: {
    //     close(){closeModal()}
    // }
}
</script>

<style>
    .pop-up{
        display: inline-block;
        background-color: white !important;
        border-radius: 5px;
        padding: 20px 50px;
        text-align: center;
        border: 2px solid black;
        box-shadow: 5px 10px #88888888;
    }
</style>