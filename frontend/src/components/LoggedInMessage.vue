<template>
    <div class="modal-overlay">
        <div class="pop-up">
            <h2>{{ header }}</h2>
            <p>{{ message }}</p>
            <button @click="handleClose" class="welcome-btn">Ok</button>
        </div>
    </div>
</template>

<script>

import { closeModal } from "jenesius-vue-modal";

export default {
    name: 'LoggedInMessage',
    props: ['header', 'message'],
    methods: {
        handleClose() {
            closeModal();
        }
    }
}
</script>

<style>
.welcome-btn{
    background-color: var(--blue);
    color: var(--secondary);
    padding: 5px 20px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    margin-top: 20px;
}

.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0, 0, 0);
    display: flex;
    align-items: center;
    justify-content: center;

}

.pop-up {
    background-color: #181818;
    border-radius: 10px;
    padding: 20px 50px;
    text-align: center;
    border: 2px solid black;
    box-shadow: 5px 10px #e5091427 ;
    background-image: linear-gradient(to left, rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.1));
    z-index: 1000;
    position: absolute;
    top: 20%;
    left: 40%;   
}
</style>
