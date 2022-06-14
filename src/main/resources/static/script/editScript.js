
function initAutocomplete() {
    let names = document.getElementsByClassName("google")
    for (let i = 0; i < 10; i++) {
        new google.maps.places.Autocomplete(
            (names[i]),
            {types: ['geocode']}
        );
    }

}