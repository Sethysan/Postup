<template>
    <div class="page-loader">
        <div class="cube"></div>
        <div class="cube"></div>
        <div class="cube"></div>
        <div class="cube"></div>
    </div>
</template>

<script>
export default {
    
}
</script>

<style lang="scss" scoped>
@import "../styles/variables.scss";

.page-loader {
    display: flex;
    justify-content: center;
    align-content: center;
    top: 0;
    left: 0;
    width: 100vh;
    height: 100vh;
    background: transparent;
    z-index: 999;
}

.cube {
    width: 40px;
    height: 40px;
    margin-right: 10px;
}
</style>