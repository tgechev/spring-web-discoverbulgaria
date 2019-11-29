// Initialize the platform object:
var platform = new H.service.Platform({
    'apikey': '_NHpWdVPwg24udbhSq1dNlk0CSUZKICUOUeAvQTMSpQ'
});

// Obtain the default map types from the platform object
var maptypes = platform.createDefaultLayers();

// Instantiate (and display) a map object:
var map = new H.Map(
    document.getElementById('mapContainer'),
    maptypes.raster.satellite.map,
    {
        zoom: 7.5,
        center: { lat: 42.63458, lng: 25.57753 }
    });

// Enable the event system on the map instance:
var mapEvents = new H.mapevents.MapEvents(map);

// Instantiate the default behavior, providing the mapEvents object:
var behavior = new H.mapevents.Behavior(mapEvents);

// Create the default UI:
var ui = H.ui.UI.createDefault(map, maptypes);