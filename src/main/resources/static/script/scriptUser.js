const tb = document.getElementsByTagName("table")
const pag = document.getElementsByClassName("pagination")
if (pag.length !== 0) {
    pag[0].scrollIntoView(false)
} else {
    if (tb.length !== 0) {
        tb[tb.length - 1].scrollIntoView(false)
    }
}