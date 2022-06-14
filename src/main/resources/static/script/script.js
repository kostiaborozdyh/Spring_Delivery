const t1 = document.getElementById('textCityFrom')
const t2 = document.getElementById('textCityTo')
const tb = document.getElementsByTagName("table")
const pag = document.getElementsByClassName("pagination")
if (pag.length !== 0) {
    pag[0].scrollIntoView(false)
} else {
    if (tb.length !== 0) {
        tb[tb.length - 1].scrollIntoView(false)
    }
}
let a2 = 1;
let a1 = 1;
t2.addEventListener('click', foo2);
t1.addEventListener('click', foo1);

function foo1() {
    a1 = a1 + 1
    document.getElementById(`idCityFrom${a1}`).style.display="block"
    document.getElementById(`editCityFrom${a1}`).setAttribute("required","required")
    if (a1 === 5) t1.style.visibility = "hidden"
}

function foo2() {
    a2 = a2 + 1
    document.getElementById(`idCityTo${a2}`).style.display="block"
    document.getElementById(`editCityTo${a2}`).setAttribute("required","required")
    if (a2 === 5) t2.style.visibility = "hidden"
}