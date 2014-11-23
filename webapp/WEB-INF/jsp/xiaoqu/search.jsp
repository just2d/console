  首字母:
        <select name="ch" id="ch" onchange="setCityInfo(this.value)">
            <option value="">
                -请选择-
            </option>
            <option value="a">
                A
            </option>
            <option value="b">
                B
            </option>
            <option value="c">
                C
            </option>
            <option value="d">
                D
            </option>
            <option value="e">
                E
                <J /option>
                <option value="f">
                    F
                </option>
                <option value="g">
                    G
                </option>
                <option value="h">
                    H
                </option>
                <option value="i">
                    I
                </option>
                <option value="j">
                    J
                </option>
                <option value="k">
                    K
                </option>
                <option value="l">
                    L
                </option>
                <option value="m">
                    M
                </option>
                <option value="n">
                    N
                </option>
                <option value="o">
                    O
                </option>
                <option value="p">
                    P
                </option>
                <option value="q">
                    Q
                </option>
                <option value="r">
                    R
                </option>
                <option value="s">
                    S
                </option>
                <option value="t">
                    T
                </option>
                <option value="u">
                    U
                </option>
                <option value="v">
                    V
                </option>
                <option value="w">
                    W
                </option>
                <option value="x">
                    X
                </option>
                <option value="y">
                    Y
                </option>
                <option value="z">
                    Z
                </option>
        </select>
        城市:
        <select name="cityId" id="cityLocale"
            onchange="setDistInfo(this.value)">
            <option value="">
                -请选择-
            </option>
            <c:if test="${cityName ne null}">
                <option value="${condition.cityId}" selected="selected">
                    ${cityName}
                </option>
            </c:if>
        </select>
        区域:
        <select name="distId" id="distLocale"
            onchange="setBlockInfo(this.value)">
            <option value="">
                -请选择-
            </option>
            <c:forEach items="${distList}" var="dist">
                <option value="${dist.id}" ${dist.id eq
                    condition.distId ? 'selected=selected' :''}>
                    ${dist.name}
                </option>
            </c:forEach>
        </select>
        板块:
        <select name="blockId" id="blockLocale">
            <option value="">
                -请选择-
            </option>
            <c:forEach items="${blockList}" var="block">
                <option value="${block.id}" ${block.id eq
                    condition.blockId ? 'selected=selected' :''}>
                    ${block.name}
                </option>
            </c:forEach>
        </select>