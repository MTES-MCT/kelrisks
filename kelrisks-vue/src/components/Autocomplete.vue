<template>
  <div class="autocomplete"><input @input="onChange"
                                   @keydown.down="onArrowDown"
                                   @keydown.enter="onEnter"
                                   @keydown.up="onArrowUp"
                                   type="text"
                                   v-model="query"/>
    <div class="loading"
         v-if="isLoading">@
    </div>
    <ul class="autocomplete-results"
        id="autocomplete-results"
        v-show="isOpen">
      <li :class="{ 'is-active': i === arrowCounter }"
          :key="i"
          @click="setResult(result.code)"
          class="autocomplete-result"
          v-for="(result, i) in results">{{ result.libelle }}
      </li>
    </ul>
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
    startAt: {
      type: Number,
      default: 3
    }
  },

  data () {
    return {
      isOpen: false,
      results: [],
      query: '',
      isLoading: false,
      arrowCounter: 0
    }
  },

  methods: {
    onChange () {
      // Let's warn the parent that a change was made
      this.$emit('input', this.query)

      console.log('onChange')
      console.log(this.startAt)
      console.log(this.query.length)

      if (this.query.length >= this.startAt) {
        console.log(this)
        console.log(this.query)
        console.log(this.source)

        // Is the data given by an outside ajax request
        if (typeof this.source === 'string') {
          this.isLoading = true
          fetch(this.source + this.query)
            .then(stream => stream.json())
            .then(value => {
              console.log(value)
              this.isLoading = false
              // if (value.entity.length > 0) {
              this.isOpen = true
              console.log(value.entity)
              this.results = value.entity
              // }
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
      this.query = result
      this.isOpen = false
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
      this.query = this.results[this.arrowCounter]
      this.isOpen = false
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
    width    : 100%;
  }

  .autocomplete-result {
    list-style : none;
    text-align : left;
    padding    : 4px 2px;
    cursor     : pointer;
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
