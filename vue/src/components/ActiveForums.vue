<template>
    <div>
        <h1>Active Forums</h1>
        <ul>
            <li v-for="forum in forums" :key="forum.id">
                {{ forum.name }}
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    data() {
        return {
            forums: [
                { id: 1, name: 'Forum 1' },
                { id: 2, name: 'Forum 2' },
                { id: 3, name: 'Forum 3' }
            ]
        };
    }
};
</script>

<style scoped>
h1 {
    color: blue;
}
</style>