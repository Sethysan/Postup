<template>
    <div class="pop-up">
    <h2>Welcome, {{ username }}</h2>
    <p>You are now logged in to Post-Up</p>
    <!-- <button @click="close">Ok</button> -->
    </div>
</template>

<script>
import {closeModal} from 'jenesius-vue-modal';

export default {
    name: 'LoggedInMessage',
    props: ['username'],
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