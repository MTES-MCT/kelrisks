<template>
    <div class="autocomplete"><input @input="onChange"
                                     @keydown.down="onArrowDown"
                                     @keydown.enter="onEnter"
                                     @keydown.up="onArrowUp"
                                     type="text"
                                     v-model="search"/>
        <ul class="autocomplete-results"
            id="autocomplete-results"
            v-show="isOpen">
            <li class="loading"
                v-if="isLoading">Loading results...
            </li>
            <li :class="{ 'is-active': i === arrowCounter }"
                :key="i"
                @click="setResult(result)"
                class="autocomplete-result"
                v-else
                v-for="(result, i) in results">{{ result }}
            </li>
        </ul>
    </div>
</template>

<script src="autocomplete.js"></script>
<script src="autocomplete.css"></script>