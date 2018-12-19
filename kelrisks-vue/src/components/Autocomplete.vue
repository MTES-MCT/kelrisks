<template>
  <div class="form__group">
    <div class="autocomplete">
      <label :for="id">{{ labelText }}</label>
      <input :id="id"
             :name="name + '.libelle'"
             :placeholder="placeholder"
             :type="type"
             @input="onChange"
             @keydown.down="onArrowDown"
             @keydown.enter="onEnter"
             @keydown.up="onArrowUp"
             v-bind:class="{'autocomplete-no-results':hasNoResults}"
             v-model="query"/>
      <input :name="name + '.code'"
             type="hidden"
             v-model="code"/>
      <div class="loading"
           v-if="isLoading"><i class="fas fa-spinner fa-spin"></i></div>
      <ul class="autocomplete-results"
          id="autocomplete-results"
          v-show="isOpen">
        <li :class="{ 'is-active': i === arrowCounter }"
            :key="i"
            @click="setResult(result)"
            class="autocomplete-result"
            v-for="(result, i) in results">{{ result.libelle }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'autocomplete',

  props: {
    items: {
      type: Array,
      required: false,
      default: () => []
    },
    source: {
      type: [String, Array],
      required: true
    },
    labelText: {
      type: String,
      required: true
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
      required: false
    },
    type: {
      type: String,
      required: false,
      default: 'text'
    },
    startAt: {
      type: Number,
      default: 3
    }
  },

  data () {
    return {
      isOpen: false,
      results: [],
      isLoading: false,
      query: '',
      code: '',
      arrowCounter: 0,
      hasNoResults: false
    }
  },

  methods: {
    onChange () {
      // Let's warn the parent that a change was made
      console.log(this.query)
      this.$emit('input', this.query)

      if (this.query.length >= this.startAt) {
        // Is the data given by an outside ajax request
        if (typeof this.source === 'string') {
          this.isLoading = true
          this.isOpen = false
          fetch(this.source + this.query)
            .then(stream => stream.json())
            .then(value => {
              this.isLoading = false
              if (value.entity.length > 0) {
                this.isOpen = true
                this.results = value.entity
                this.hasNoResults = false
              } else {
                this.hasNoResults = true
                this.isOpen = false
              }
            })
          // Else, let's filter our flat array
        } else {
          this.filterResults()
          this.isOpen = true
        }
      }
    },

    filterResults () {
      // first uncapitalize all the things
      this.results = this.items.filter((item) => {
        return item.toLowerCase().indexOf(this.query.toLowerCase()) > -1
      })
    },
    setResult (result) {
      this.query = result.libelle
      this.code = result.code
      this.isOpen = false
      this.$emit('input', this.code)
    },
    onArrowDown (evt) {
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
  mounted () {
    document.addEventListener('click', this.handleClickOutside)
  },
  destroyed () {
    document.removeEventListener('click', this.handleClickOutside)
  }
}
</script>

<style>
  .autocomplete {
    position : relative;
  }

  .autocomplete-results {
    padding  : 0;
    margin   : 0;
    border   : 1px solid #EEEEEE;
    height   : 120px;
    overflow : auto;
    width    : 100%
  }

  .autocomplete-result {
    list-style : none;
    text-align : left;
    padding    : 4px 2px;
    cursor     : pointer;
  }

  .autocomplete-no-results * {
    background-color : hsl(0, 50%, 76%);
    border-color     : hsl(0, 50%, 56%);
  }

  .autocomplete-no-results:hover {
    background-color : hsl(0, 50%, 60%);
    border-color     : hsl(0, 50%, 40%);
  }

  .autocomplete-no-results:focus {
    background-color : hsl(0, 50%, 44%);
    border-color     : hsl(0, 50%, 22%);
  }

  .autocomplete-result.is-active,
  .autocomplete-result:hover {
    background-color : #4AAE9B;
    color            : white;
  }

  .loading {
    position : absolute;
    right    : 10px;
    top      : 12px;
  }

</style>
