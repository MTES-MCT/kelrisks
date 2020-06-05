<template>
    <div class="kr-group">
        <label :for="getId"
               v-bind:class="{'focus':inputHasFocus,
                          'top':inputHasFocus || query !== '',
                          'error': errors.length > 0}"
               v-if="label !== ''">{{ label }}</label>
        <!--suppress HtmlFormInputWithoutLabel -->
        <input :id="getId"
               :name="name"
               :title="title"
               :type="type"
               @blur="onBlur"
               @click="onOpen"
               @focus="onFocus"
               @input="onChange"
               @keydown.down="onArrowDown"
               @keydown.enter="onEnter"
               @keydown.up="onArrowUp"
               v-bind:class="{'kr-no-results':hasNoResults,
                          'error': errors.length > 0}"
               v-model="query"/>
        <button @click="clearField"
                class="kr-clear"
                v-if="query">
            <font-awesome-icon icon="times"/>
        </button>
        <input :id="getId + '_code'"
               :name="name + '.code'"
               type="hidden"
               v-if="source"
               v-model="code"/>
        <div class="kr-loading-wrapper"
             v-if="isLoading && inputHasFocus">
            <div class="kr-loading">
                <font-awesome-icon icon="spinner"
                                   spin/>
            </div>
        </div>
        <div class="kr-no-results-wrapper"
             v-if="hasNoResults && inputHasFocus">
            <div class="kr-no-results">
                <slot name="kr-no-results"
                      v-bind:query="query">Aucun résultat trouvé pour "{{ query }}".
                </slot>
            </div>
        </div>
        <div class="kr-helper"
             v-bind:class="{'error' : errors.length > 0,
                        'warning' : warnings.length > 0,
                        'info' : infos.length > 0}"
             v-if="errors.length > 0 || warnings.length > 0 || infos.length > 0">
            <strong>
                <template v-if="errors.length > 0">
                    <template v-if="Array.isArray(errors)">
                        <li v-bind:key="err"
                            v-for="err in errors">{{ err }}
                        </li>
                    </template>
                    <template v-else>{{ errors }}</template>
                </template>
                <template v-else-if="warnings.length > 0">
                    <template v-if="Array.isArray(warnings)">
                        <li v-bind:key="warn"
                            v-for="warn in warnings">{{ warn }}
                        </li>
                    </template>
                    <template v-else>{{ warnings }}</template>
                </template>
                <template v-else-if="infos.length > 0">
                    <template v-if="Array.isArray(infos)">
                        <li v-bind:key="inf"
                            v-for="inf in infos">{{ inf }}
                        </li>
                    </template>
                    <template v-else>{{ infos }}</template>
                </template>
            </strong>
        </div>
        <div class="kr-helper"
             v-else-if="selectedOption">
            <strong>
                <slot name="kr-helper"
                      v-bind:option="selectedOption"></slot>
            </strong>
        </div>
        <div class="kr-helper"
             v-else-if="placeholder.length > 0">
            {{placeholder}}
        </div>
        <div class="kr-autocomplete-options-wrapper"
             v-if="source"
             v-show="isOpen">
            <ul :id="getId + '_autocomplete_options'"
                class="kr-autocomplete-options">
                <li :class="{ 'is-active': i === arrowCounter }"
                    :key="i"
                    @click="setResult(option)"
                    class="kr-autocomplete-option"
                    v-for="(option, i) in results">
                    <slot name="kr-option-label"
                          v-bind:option="option">{{ getOptionLabelFunction(option) }}
                    </slot>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import fetchWithError from '../../script/fetchWithError'

export default {
    name: 'kr-input',
    props: {
        value: {
            type: String,
            required: false,
            default: ''
        },
        options: {
            type: Array,
            required: false,
            default: () => []
        },
        source: {
            type: [String, Array],
            required: false
        },
        label: {
            type: String,
            default: '',
            required: false
        },
        id: {
            type: String,
            required: false
        },
        name: {
            type: String,
            required: true
        },
        placeholder: {
            type: String,
            required: false,
            default: ''
        },
        title: {
            type: String,
            required: false
        },
        type: {
            type: String,
            required: false,
            default: 'text'
        },
        autocompleteStartAt: {
            type: Number,
            default: 3
        },
        getOptionValueFunction: {
            type: Function,
            default: function (option) {
                // console.log(option)
                // console.log(option['code'])
                return option['code']
            },
            required: false
        },
        getOptionLabelFunction: {
            type: Function,
            default: function (option) {
                // console.log(option)
                // console.log(option['libelle'])
                return option['libelle']
            },
            required: false
        },
        openOnFocus: {
            type: Boolean,
            default: true
        },
        startAt: {
            type: Number,
            default: 3
        },
        errors: {
            type: [String, Array],
            default: () => []
        },
        warnings: {
            type: [String, Array],
            default: ''
        },
        infos: {
            type: [String, Array],
            default: ''
        },
        searchDelay: {
            type: Number,
            default: 500
        },
        getResultsListFunction: {
            type: Function,
            default: function (data) {
                return data['entity']
            },
            required: false
        },
        searchQuerySeparator: {
            type: String,
            default: ' ',
            required: false
        }
    },
    data () {
        return {
            isOpen: false,
            inputHasFocus: false,
            selectedOption: null,
            results: [],
            isLoading: false,
            code: '',
            query: '',
            arrowCounter: 0,
            hasNoResults: false,
            searchDelayInstance: null,
            changeDelayInstance: null,
            isAutocomplete: undefined !== this.source,
            reference: null
        }
    },

    methods: {
        onOpen () {
        },
        onFocus () {
            this.inputHasFocus = true

            if (this.openOnFocus && this.isAutocomplete) {
                this.search()
            }
        },
        onBlur () {
            this.inputHasFocus = false

            if (this.selectedOption && this.query !== this.selectedOption[this.optionLabelProperty]) {
                this.query = this.selectedOption[this.optionLabelProperty]
            }
        },
        onChange () {
            // console.log('onChange')
            if (this.isAutocomplete) {
                this.$emit('input', this.code)
                if (!this.query || this.query === '') this.clearField()
                this.search()
            } else {
                this.$emit('input', this.query)
            }

            this.$emit('query', this.query)

            if (this.changeDelayInstance) clearTimeout(this.changeDelayInstance)
            this.changeDelayInstance = setTimeout(() => {
                this.$emit('delayedquery', this.query)
            }, 500)
        },
        search () {
            if (this.searchDelayInstance) clearTimeout(this.searchDelayInstance)
            this.searchDelayInstance = setTimeout(() => {
                if (this.query && this.query.length >= this.startAt) {
                    if (typeof this.source === 'string') {
                        this.hasNoResults = false
                        this.isLoading = true
                        this.isOpen = false
                        fetchWithError(this.source + this.query.replace(/ /g, this.searchQuerySeparator), null, 1000 * 10)
                            .then(stream => stream.json())
                            .then(data => {
                                this.isLoading = false
                                if (this.getResultsListFunction(data).length > 0) {
                                    this.isOpen = true
                                    this.results = this.getResultsListFunction(data)
                                } else {
                                    this.hasNoResults = true
                                    this.isOpen = false
                                }
                            })
                        // TODO : Else, let's filter our flat array
                    } else {
                        this.filterResults()
                        this.isOpen = true
                    }
                }
            }, this.searchDelay)
        },
        clearField () {
            this.selectedOption = null
            this.query = ''
            this.isOpen = false
            this.hasNoResults = false
            this.$emit('selected', '')
            this.$emit('input', '')
            this.$emit('query', '')
        },
        filterResults () {
            // first uncapitalize all the things
            this.results = this.items.filter((item) => {
                return item.toLowerCase().indexOf(this.query.toLowerCase()) > -1
            })
        },
        setResult (option) {
            this.selectedOption = option
            this.query = this.getOptionLabelFunction(option)
            this.code = this.getOptionValueFunction(option)
            this.isOpen = false
            this.$emit('selected', option)
        },
        onArrowDown () {
            if (this.arrowCounter < this.results.length) {
                this.arrowCounter = this.arrowCounter + 1
            }
        },
        onArrowUp () {
            if (this.arrowCounter > 0) {
                this.arrowCounter = this.arrowCounter - 1
            }
        },
        onEnter () {
            this.setResult(this.results[this.arrowCounter])
            this.arrowCounter = -1
        },
        handleClickOutside (evt) {
            if (!this.$el.contains(evt.target)) {
                this.isOpen = false
                this.arrowCounter = -1
            }
        }
    },
    watch: {
        items: function (val, oldValue) {
            // actually compare them
            if (val.length !== oldValue.length) {
                this.results = val
                this.isLoading = false
            }
        }
    },
    computed: {
        getId () {
            return this.id ? this.id : 'kr_' + this.reference
        }
    },
    mounted () {
        document.addEventListener('click', this.handleClickOutside)

        this.reference = this._uid
    },
    destroyed () {
        document.removeEventListener('click', this.handleClickOutside)
    }
}
</script>

<style>

    .kr-group {
        position : relative;
    }

    .kr-group label {
        position       : absolute;
        z-index        : 7;
        top            : 24px;
        left           : 0;
        pointer-events : none;
        transition     : all .1s;
    }

    .kr-group label.top {
        color     : #555555;
        position  : absolute;
        font-size : 0.75em;
        top       : 0;
        left      : 0;
    }

    .kr-group label.focus {
        color : #0053B3;
    }

    .kr-group label.error {
        color : #CC5250;
    }

    .kr-group input {
        border-radius    : unset;
        border           : none;
        border-bottom    : solid 1px #CCCCCC;
        margin-bottom    : 24px;
        padding          : 20px 0 3px;
        background-color : #FFFFFF;
        z-index          : 5;
    }

    .kr-group input:focus {
        border-bottom : solid 2px #0053B3;
        margin-bottom : 23px;
    }

    .kr-group input.error {
        border-bottom-color : #CC5250;
    }

    .kr-clear {
        background : none;
        border     : none;
        position   : absolute;
        right      : 8px;
        top        : 15px;
        padding    : 8px 12px;
        width      : 32px;
        height     : 32px;
        z-index    : 7;
        color      : #333333;
    }

    .kr-clear svg {
        margin : 0 10px 0 0;
    }

    .kr-helper {
        color      : #888888;
        font-size  : 0.7em;
        position   : absolute;
        text-align : left;
        left       : 0;
        z-index    : 2;
        top        : 50px;
    }

    .kr-helper li {
        list-style : none;
    }

    .kr-helper.info {
        color : #52A3CC;
    }

    .kr-helper.warning {
        color : #CC7750;
    }

    .kr-helper.error {
        color : #CC5250;
    }

    .kr-autocomplete-options-wrapper,
    .kr-loading-wrapper,
    .kr-no-results-wrapper {
        position : absolute;
        top      : 50px;
        left     : -20px;
        z-index  : 10;
        overflow : hidden;
    }

    .kr-autocomplete-options,
    .kr-loading,
    div.kr-no-results {
        background-color : #FFFFFF;
        margin           : 0 20px 20px 20px;
        padding          : 10px;
        box-shadow       : #999999 0 0 20px;
        border           : 1px solid #CCCCCC;
        min-width        : 250px;
    }

    .kr-loading {
        font-size  : 1.5em;
        min-height : 40px;
        text-align : left;
    }

    .kr-autocomplete-options {
        overflow-y : auto;
        max-height : 250px;
        padding    : 10px 0;
    }

    .kr-autocomplete-option {
        list-style       : none;
        text-align       : left;
        padding          : 8px;
        background-color : #FFFFFF;
        transition       : all 0.1s;
    }

    .kr-autocomplete-option.is-active {
        background-color : #CCCCCC;
    }

    .kr-autocomplete-option:hover {
        background-color : #CCCCCC;
    }

    .kr-clear:hover {
        background-color : #CCCCCC;
        border-radius    : 17px;
    }

</style>
