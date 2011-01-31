modules = {
  'planningPoker' {
      dependsOn 'icescrum'
      resource url: [dir: "css", file: 'planningPoker.css', plugin:'icescrum-plugin-planning-poker'], attrs: [media: 'screen,projection'], bundle:'icescrum'
      resource url: [dir: 'js/jquery', file: 'jquery.icescrum.planningpoker.js', plugin:'icescrum-plugin-planning-poke'],disposition: 'head', bundle:'icescrum'
  }
}