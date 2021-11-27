function fillTable(data) {
    const tbody = document.querySelector(`.user_table_tbody`)
    let index = 0
    for (let user of data) {
        let row = document.createElement('tr')

        const roles = user['roles']
        const rolesString = convertRolesToString(roles)
        row.innerHTML = `
            <td>${user['user_id']}</td>
            <td>${user['name']}</td>
            <td>${user['lastname']}</td>
            <td>${user['yearOfBirth']}</td>
            <td>${user['username']}</td>
            <td>${rolesString.length === 0 ? 'NO ROLES' : rolesString}</td>
        `
        tbody.appendChild(row)

        index++
    }
}

function fillHeading(currentUser) {
    const heading = document.querySelector(`.heading_info`)
    let spanEmail = document.createElement('span')
    spanEmail.classList.add('font-weight-bold')
    spanEmail.textContent = `${currentUser['username']}`
    let spanRoles = document.createElement('span')
    spanRoles.textContent = ` with roles: ${convertRolesToString(currentUser['roles'])}`
    let elements = heading.getElementsByTagName('span')
    while (elements[0]) elements[0].parentNode.removeChild(elements[0])
    heading.appendChild(spanEmail)
    heading.appendChild(spanRoles)
}

function fillHeadingAndTable() {
    fetch("http://localhost:8080/api/current-user")
        .then(res => {
            return res.json()
        })
        .then(data => {
            let currentUser = data
            console.log('CurrentUser', currentUser)
            fillHeading(currentUser)
            fillTable([currentUser])
        })
}

fillHeadingAndTable()

