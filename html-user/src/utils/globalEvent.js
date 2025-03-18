import mitt from 'mitt'

const emitter = mitt()

export const GlobalEvents = {
  SHOW_LOGIN: 'SHOW_LOGIN'
}

export default emitter