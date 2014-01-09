<g:if test="${request.inProduct}">
    <li class="navigation-item"></li>
    <li class="navigation-item">
        <a class="tool-button button-n"
           href="#planningPoker"
           title="${message(code:'is.ui.planningPoker')}">
            <span class="start"></span>
            <span class="content">
                <span class="ico"></span>
                ${message(code: 'is.ui.planningPoker')}
            </span>
            <span class="end"></span>
        </a>
    </li>
</g:if>