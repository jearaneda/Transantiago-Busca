angular.module('app.controllers', [])
  
.controller('busesCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams) {

$scope.data = {
    "note" : ""
} 
}])
   
.controller('serviciosAlternativosCtrl', ['$scope', '$stateParams', '$firebaseArray', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams, $firebaseArray) {


$scope.data = {
    "note" : $stateParams.note,
    "message" : ""
};

var ref = firebase.database().ref();
var asd = $scope.data.note;

$scope.messages = $firebaseArray(ref);
console.log($scope.messages)
$scope.items = [
 {
   "id": "F01",
   "grupo": 1,
   "alt": "F01"
 },
 {
   "id": "F01c",
   "grupo": 1,
   "alt": "F01c"
 },
 {
   "id": "B01",
   "grupo": 1,
   "alt": "B01"
 },
 {
   "id": "501",
   "grupo": 1,
   "alt": "501"
 },
 {
   "id": "401",
   "grupo": 1,
   "alt": "401"
 },
 {
   "id": "401N",
   "grupo": 1,
   "alt": "401c2"
 },
 {
   "id": "301",
   "grupo": 1,
   "alt": "301"
 },
 {
   "id": "301c",
   "grupo": 1,
   "alt": "301c"
 },
 {
   "id": "301c2",
   "grupo": 1,
   "alt": "301c2"
 },
 {
   "id": "301e",
   "grupo": 1,
   "alt": "301e"
 },
 {
   "id": "201",
   "grupo": 1,
   "alt": "201"
 },
 {
   "id": "201c",
   "grupo": 1,
   "alt": "201c"
 },
 {
   "id": "201e",
   "grupo": 1,
   "alt": "201e"
 },
 {
   "id": "101",
   "grupo": 1,
   "alt": "101"
 },
 {
   "id": "101c",
   "grupo": 1,
   "alt": "101c"
 },
 {
   "id": "F02",
   "grupo": 2,
   "alt": "F02"
 },
 {
   "id": "B02",
   "grupo": 2,
   "alt": "B02"
 },
 {
   "id": "B02N",
   "grupo": 2,
   "alt": "B02N"
 },
 {
   "id": "502",
   "grupo": 2,
   "alt": "502"
 },
 {
   "id": "502c",
   "grupo": 2,
   "alt": "502c"
 },
 {
   "id": "402",
   "grupo": 2,
   "alt": "402"
 },
 {
   "id": "302",
   "grupo": 2,
   "alt": "302"
 },
 {
   "id": "302N",
   "grupo": 2,
   "alt": "302N"
 },
 {
   "id": "302e",
   "grupo": 2,
   "alt": "302e"
 },
 {
   "id": "202c",
   "grupo": 2,
   "alt": "202c"
 },
 {
   "id": "102",
   "grupo": 2,
   "alt": "102"
 },
 {
   "id": "F03",
   "grupo": 3,
   "alt": "F03"
 },
 {
   "id": "F03c",
   "grupo": 3,
   "alt": "F03c"
 },
 {
   "id": "B03",
   "grupo": 3,
   "alt": "B03"
 },
 {
   "id": "503",
   "grupo": 3,
   "alt": "503"
 },
 {
   "id": "503c",
   "grupo": 3,
   "alt": "503c"
 },
 {
   "id": "403",
   "grupo": 3,
   "alt": "403"
 },
 {
   "id": "303",
   "grupo": 3,
   "alt": "303"
 },
 {
   "id": "303e",
   "grupo": 3,
   "alt": "303e"
 },
 {
   "id": "203",
   "grupo": 3,
   "alt": "203"
 },
 {
   "id": "203e",
   "grupo": 3,
   "alt": "203e"
 },
 {
   "id": "103",
   "grupo": 3,
   "alt": "103"
 },
 {
   "id": "B04",
   "grupo": 4,
   "alt": "B04"
 },
 {
   "id": "B04v",
   "grupo": 4,
   "alt": "B04v"
 },
 {
   "id": "504",
   "grupo": 4,
   "alt": "504"
 },
 {
   "id": "404",
   "grupo": 4,
   "alt": "404"
 },
 {
   "id": "404c",
   "grupo": 4,
   "alt": "404c"
 },
 {
   "id": "204",
   "grupo": 4,
   "alt": "204"
 },
 {
   "id": "204e",
   "grupo": 4,
   "alt": "204e"
 },
 {
   "id": "204",
   "grupo": 4,
   "alt": "204y"
 },
 {
   "id": "104",
   "grupo": 4,
   "alt": "104"
 },
 {
   "id": "104",
   "grupo": 4,
   "alt": "104c"
 },
 {
   "id": "F05",
   "grupo": 5,
   "alt": "F05"
 },
 {
   "id": "B05",
   "grupo": 5,
   "alt": "B05"
 },
 {
   "id": "505",
   "grupo": 5,
   "alt": "505"
 },
 {
   "id": "405",
   "grupo": 5,
   "alt": "405"
 },
 {
   "id": "405c",
   "grupo": 5,
   "alt": "405c"
 },
 {
   "id": "405c",
   "grupo": 5,
   "alt": "405cy"
 },
 {
   "id": "205",
   "grupo": 5,
   "alt": "205"
 },
 {
   "id": "205e",
   "grupo": 5,
   "alt": "205e"
 },
 {
   "id": "205",
   "grupo": 5,
   "alt": "205y"
 },
 {
   "id": "105",
   "grupo": 5,
   "alt": "105"
 },
 {
   "id": "105",
   "grupo": 5,
   "alt": "105c"
 },
 {
   "id": "F06",
   "grupo": 6,
   "alt": "F06"
 },
 {
   "id": "B06",
   "grupo": 6,
   "alt": "B06"
 },
 {
   "id": "506",
   "grupo": 6,
   "alt": "506"
 },
 {
   "id": "506e",
   "grupo": 6,
   "alt": "506e"
 },
 {
   "id": "506v",
   "grupo": 6,
   "alt": "506v"
 },
 {
   "id": "406",
   "grupo": 6,
   "alt": "406"
 },
 {
   "id": "406c",
   "grupo": 6,
   "alt": "406c"
 },
 {
   "id": "206",
   "grupo": 6,
   "alt": "206"
 },
 {
   "id": "206e",
   "grupo": 6,
   "alt": "206e"
 },
 {
   "id": "106",
   "grupo": 6,
   "alt": "106"
 },
 {
   "id": "F07",
   "grupo": 7,
   "alt": "F07"
 },
 {
   "id": "B07",
   "grupo": 7,
   "alt": "B07"
 },
 {
   "id": "B07",
   "grupo": 7,
   "alt": "B07y"
 },
 {
   "id": "507",
   "grupo": 7,
   "alt": "507"
 },
 {
   "id": "507c",
   "grupo": 7,
   "alt": "507c"
 },
 {
   "id": "407",
   "grupo": 7,
   "alt": "407"
 },
 {
   "id": "307",
   "grupo": 7,
   "alt": "307"
 },
 {
   "id": "307e",
   "grupo": 7,
   "alt": "307e"
 },
 {
   "id": "207",
   "grupo": 7,
   "alt": "207"
 },
 {
   "id": "207c",
   "grupo": 7,
   "alt": "207c"
 },
 {
   "id": "207e",
   "grupo": 7,
   "alt": "207e"
 },
 {
   "id": "107",
   "grupo": 7,
   "alt": "107"
 },
 {
   "id": "107c",
   "grupo": 7,
   "alt": "107c"
 },
 {
   "id": "107c",
   "grupo": 7,
   "alt": "107cy"
 },
 {
   "id": "F08",
   "grupo": 8,
   "alt": "F08"
 },
 {
   "id": "B08",
   "grupo": 8,
   "alt": "B08"
 },
 {
   "id": "508",
   "grupo": 8,
   "alt": "508"
 },
 {
   "id": "308",
   "grupo": 8,
   "alt": "308"
 },
 {
   "id": "208",
   "grupo": 8,
   "alt": "208"
 },
 {
   "id": "208c",
   "grupo": 8,
   "alt": "208c"
 },
 {
   "id": "108",
   "grupo": 8,
   "alt": "108"
 },
 {
   "id": "F09",
   "grupo": 9,
   "alt": "F09"
 },
 {
   "id": "B09",
   "grupo": 9,
   "alt": "B09"
 },
 {
   "id": "509",
   "grupo": 9,
   "alt": "509"
 },
 {
   "id": "409",
   "grupo": 9,
   "alt": "409"
 },
 {
   "id": "209",
   "grupo": 9,
   "alt": "209"
 },
 {
   "id": "209e",
   "grupo": 9,
   "alt": "209e"
 },
 {
   "id": "109",
   "grupo": 9,
   "alt": "109"
 },
 {
   "id": "109N",
   "grupo": 9,
   "alt": "109c"
 },
 {
   "id": "F10",
   "grupo": 10,
   "alt": "F10"
 },
 {
   "id": "B10",
   "grupo": 10,
   "alt": "B10"
 },
 {
   "id": "510",
   "grupo": 10,
   "alt": "510"
 },
 {
   "id": "510c",
   "grupo": 10,
   "alt": "510c"
 },
 {
   "id": "I24",
   "grupo": 10,
   "alt": "310"
 },
 {
   "id": "210",
   "grupo": 10,
   "alt": "210"
 },
 {
   "id": "210v",
   "grupo": 10,
   "alt": "210v"
 },
 {
   "id": "210y",
   "grupo": 10,
   "alt": "210y"
 },
 {
   "id": "110",
   "grupo": 10,
   "alt": "110"
 },
 {
   "id": "110c",
   "grupo": 10,
   "alt": "110c"
 },
 {
   "id": "F11",
   "grupo": 11,
   "alt": "F11"
 },
 {
   "id": "B11",
   "grupo": 11,
   "alt": "B11"
 },
 {
   "id": "511",
   "grupo": 11,
   "alt": "511"
 },
 {
   "id": "411",
   "grupo": 11,
   "alt": "411"
 },
 {
   "id": "211",
   "grupo": 11,
   "alt": "211"
 },
 {
   "id": "211c",
   "grupo": 11,
   "alt": "211c"
 },
 {
   "id": "211e",
   "grupo": 11,
   "alt": "211e"
 },
 {
   "id": "111",
   "grupo": 11,
   "alt": "111"
 },
 {
   "id": "111c",
   "grupo": 11,
   "alt": "111c"
 },
 {
   "id": "F12",
   "grupo": 12,
   "alt": "F12"
 },
 {
   "id": "F12c",
   "grupo": 12,
   "alt": "F12c"
 },
 {
   "id": "B12",
   "grupo": 12,
   "alt": "B12"
 },
 {
   "id": "412",
   "grupo": 12,
   "alt": "412"
 },
 {
   "id": "312e",
   "grupo": 12,
   "alt": "312e"
 },
 {
   "id": "212",
   "grupo": 12,
   "alt": "212"
 },
 {
   "id": "112",
   "grupo": 12,
   "alt": "112"
 },
 {
   "id": "112N",
   "grupo": 12,
   "alt": "112c"
 },
 {
   "id": "F13",
   "grupo": 13,
   "alt": "F13"
 },
 {
   "id": "F13c",
   "grupo": 13,
   "alt": "F13c"
 },
 {
   "id": "B13",
   "grupo": 13,
   "alt": "B13"
 },
 {
   "id": "513",
   "grupo": 13,
   "alt": "513"
 },
 {
   "id": "413c",
   "grupo": 13,
   "alt": "413c"
 },
 {
   "id": "413v",
   "grupo": 13,
   "alt": "413c2"
 },
 {
   "id": "313e",
   "grupo": 13,
   "alt": "313e"
 },
 {
   "id": "213e",
   "grupo": 13,
   "alt": "213e"
 },
 {
   "id": "213e",
   "grupo": 13,
   "alt": "213ey"
 },
 {
   "id": "113",
   "grupo": 13,
   "alt": "113"
 },
 {
   "id": "113e",
   "grupo": 13,
   "alt": "113e"
 },
 {
   "id": "F14",
   "grupo": 14,
   "alt": "F14"
 },
 {
   "id": "B14",
   "grupo": 14,
   "alt": "B14"
 },
 {
   "id": "514",
   "grupo": 14,
   "alt": "514"
 },
 {
   "id": "514c",
   "grupo": 14,
   "alt": "514c"
 },
 {
   "id": "414e",
   "grupo": 14,
   "alt": "414e"
 },
 {
   "id": "314",
   "grupo": 14,
   "alt": "314"
 },
 {
   "id": "314e",
   "grupo": 14,
   "alt": "314e"
 },
 {
   "id": "214e",
   "grupo": 14,
   "alt": "214e"
 },
 {
   "id": "214",
   "grupo": 14,
   "alt": "214"
 },
 {
   "id": "114",
   "grupo": 14,
   "alt": "114"
 },
 {
   "id": "F15",
   "grupo": 15,
   "alt": "F15"
 },
 {
   "id": "B15",
   "grupo": 15,
   "alt": "B15"
 },
 {
   "id": "515N",
   "grupo": 15,
   "alt": "515"
 },
 {
   "id": "415e",
   "grupo": 15,
   "alt": "415e"
 },
 {
   "id": "315e",
   "grupo": 15,
   "alt": "315e"
 },
 {
   "id": "115",
   "grupo": 15,
   "alt": "115"
 },
 {
   "id": "F16",
   "grupo": 16,
   "alt": "F16"
 },
 {
   "id": "B16",
   "grupo": 16,
   "alt": "B16"
 },
 {
   "id": "516",
   "grupo": 16,
   "alt": "516"
 },
 {
   "id": "416e",
   "grupo": 16,
   "alt": "416e"
 },
 {
   "id": "316e",
   "grupo": 16,
   "alt": "316e"
 },
 {
   "id": "216",
   "grupo": 16,
   "alt": "216"
 },
 {
   "id": "216",
   "grupo": 16,
   "alt": "216y"
 },
 {
   "id": "116",
   "grupo": 16,
   "alt": "116"
 },
 {
   "id": "F17",
   "grupo": 17,
   "alt": "F17"
 },
 {
   "id": "B17",
   "grupo": 17,
   "alt": "B17"
 },
 {
   "id": "517",
   "grupo": 17,
   "alt": "517"
 },
 {
   "id": "417e",
   "grupo": 17,
   "alt": "417e"
 },
 {
   "id": "217e",
   "grupo": 17,
   "alt": "217e"
 },
 {
   "id": "117",
   "grupo": 17,
   "alt": "117"
 },
 {
   "id": "117c",
   "grupo": 17,
   "alt": "117c"
 },
 {
   "id": "F18",
   "grupo": 18,
   "alt": "F18"
 },
 {
   "id": "B18",
   "grupo": 18,
   "alt": "B18"
 },
 {
   "id": "B18e",
   "grupo": 18,
   "alt": "B18e"
 },
 {
   "id": "518",
   "grupo": 18,
   "alt": "518"
 },
 {
   "id": "418",
   "grupo": 18,
   "alt": "418"
 },
 {
   "id": "218e",
   "grupo": 18,
   "alt": "218e"
 },
 {
   "id": "118",
   "grupo": 18,
   "alt": "118"
 },
 {
   "id": "F19",
   "grupo": 19,
   "alt": "F19"
 },
 {
   "id": "B19",
   "grupo": 19,
   "alt": "B19"
 },
 {
   "id": "419",
   "grupo": 19,
   "alt": "419"
 },
 {
   "id": "419",
   "grupo": 19,
   "alt": "419y"
 },
 {
   "id": "219e",
   "grupo": 19,
   "alt": "219e"
 },
 {
   "id": "219e",
   "grupo": 19,
   "alt": "219ey"
 },
 {
   "id": "119",
   "grupo": 19,
   "alt": "119"
 },
 {
   "id": "519e",
   "grupo": 19,
   "alt": "519e"
 },
 {
   "id": "F20",
   "grupo": 20,
   "alt": "F20"
 },
 {
   "id": "B20",
   "grupo": 20,
   "alt": "B20"
 },
 {
   "id": "420e",
   "grupo": 20,
   "alt": "420e"
 },
 {
   "id": "120",
   "grupo": 20,
   "alt": "120"
 },
 {
   "id": "F21",
   "grupo": 21,
   "alt": "F21"
 },
 {
   "id": "B21",
   "grupo": 21,
   "alt": "B21"
 },
 {
   "id": "J01",
   "grupo": 21,
   "alt": "521"
 },
 {
   "id": "J01c",
   "grupo": 21,
   "alt": "521c"
 },
 {
   "id": "J01y",
   "grupo": 21,
   "alt": "521y"
 },
 {
   "id": "J01y",
   "grupo": 21,
   "alt": "521y "
 },
 {
   "id": "421",
   "grupo": 21,
   "alt": "421"
 },
 {
   "id": "421",
   "grupo": 21,
   "alt": "421y"
 },
 {
   "id": "321",
   "grupo": 21,
   "alt": "321"
 },
 {
   "id": "221e",
   "grupo": 21,
   "alt": "221e"
 },
 {
   "id": "121",
   "grupo": 21,
   "alt": "121"
 },
 {
   "id": "F22",
   "grupo": 22,
   "alt": "F22"
 },
 {
   "id": "B22",
   "grupo": 22,
   "alt": "B22"
 },
 {
   "id": "J02",
   "grupo": 22,
   "alt": "522"
 },
 {
   "id": "422",
   "grupo": 22,
   "alt": "422"
 },
 {
   "id": "322",
   "grupo": 22,
   "alt": "322"
 },
 {
   "id": "222e",
   "grupo": 22,
   "alt": "222e"
 },
 {
   "id": "122",
   "grupo": 22,
   "alt": "122"
 },
 {
   "id": "F23",
   "grupo": 23,
   "alt": "F23"
 },
 {
   "id": "B23",
   "grupo": 23,
   "alt": "B23"
 },
 {
   "id": "J03",
   "grupo": 23,
   "alt": "523"
 },
 {
   "id": "423",
   "grupo": 23,
   "alt": "423"
 },
 {
   "id": "223",
   "grupo": 23,
   "alt": "223"
 },
 {
   "id": "323",
   "grupo": 23,
   "alt": "323"
 },
 {
   "id": "F24",
   "grupo": 24,
   "alt": "F24"
 },
 {
   "id": "B24",
   "grupo": 24,
   "alt": "B24"
 },
 {
   "id": "J04",
   "grupo": 24,
   "alt": "524"
 },
 {
   "id": "J04c",
   "grupo": 24,
   "alt": "524c"
 },
 {
   "id": "424",
   "grupo": 24,
   "alt": "424"
 },
 {
   "id": "224",
   "grupo": 24,
   "alt": "224"
 },
 {
   "id": "224c",
   "grupo": 24,
   "alt": "224c"
 },
 {
   "id": "224n",
   "grupo": 24,
   "alt": "224c2"
 },
 {
   "id": "F25",
   "grupo": 25,
   "alt": "F25"
 },
 {
   "id": "F25e",
   "grupo": 25,
   "alt": "F25e"
 },
 {
   "id": "B25",
   "grupo": 25,
   "alt": "B25"
 },
 {
   "id": "J05",
   "grupo": 25,
   "alt": "525"
 },
 {
   "id": "425",
   "grupo": 25,
   "alt": "425"
 },
 {
   "id": "225",
   "grupo": 25,
   "alt": "225"
 },
 {
   "id": "125",
   "grupo": 25,
   "alt": "125"
 },
 {
   "id": "F26",
   "grupo": 26,
   "alt": "F26"
 },
 {
   "id": "B26",
   "grupo": 26,
   "alt": "B26"
 },
 {
   "id": "J06",
   "grupo": 26,
   "alt": "526"
 },
 {
   "id": "426",
   "grupo": 26,
   "alt": "426"
 },
 {
   "id": "226",
   "grupo": 26,
   "alt": "226"
 },
 {
   "id": "226",
   "grupo": 26,
   "alt": "226y"
 },
 {
   "id": "126",
   "grupo": 26,
   "alt": "126"
 },
 {
   "id": "B27",
   "grupo": 27,
   "alt": "B27"
 },
 {
   "id": "J07",
   "grupo": 27,
   "alt": "527"
 },
 {
   "id": "J07c",
   "grupo": 27,
   "alt": "527c"
 },
 {
   "id": "J07e",
   "grupo": 27,
   "alt": "527e"
 },
 {
   "id": "427",
   "grupo": 27,
   "alt": "427"
 },
 {
   "id": "227",
   "grupo": 27,
   "alt": "227"
 },
 {
   "id": "F27",
   "grupo": 27,
   "alt": "F27"
 },
 {
   "id": "F28N",
   "grupo": 28,
   "alt": "F28N"
 },
 {
   "id": "B28",
   "grupo": 28,
   "alt": "B28"
 },
 {
   "id": "J08",
   "grupo": 28,
   "alt": "528"
 },
 {
   "id": "J08c",
   "grupo": 28,
   "alt": "528c"
 },
 {
   "id": "428",
   "grupo": 28,
   "alt": "428"
 },
 {
   "id": "428c",
   "grupo": 28,
   "alt": "428c"
 },
 {
   "id": "428e",
   "grupo": 28,
   "alt": "428e"
 },
 {
   "id": "228",
   "grupo": 28,
   "alt": "228"
 },
 {
   "id": "408",
   "grupo": 28,
   "alt": "128"
 },
 {
   "id": "408e",
   "grupo": 28,
   "alt": "128e"
 },
 {
   "id": "B29",
   "grupo": 29,
   "alt": "B29"
 },
 {
   "id": "429",
   "grupo": 29,
   "alt": "429"
 },
 {
   "id": "429c",
   "grupo": 29,
   "alt": "429c"
 },
 {
   "id": "229",
   "grupo": 29,
   "alt": "229"
 },
 {
   "id": "F30N",
   "grupo": 30,
   "alt": "F30N"
 },
 {
   "id": "B30N",
   "grupo": 30,
   "alt": "B30N"
 },
 {
   "id": "J10",
   "grupo": 30,
   "alt": "530"
 },
 {
   "id": "430",
   "grupo": 30,
   "alt": "430"
 },
 {
   "id": "430y",
   "grupo": 30,
   "alt": "430y"
 },
 {
   "id": "230",
   "grupo": 30,
   "alt": "230"
 },
 {
   "id": "410",
   "grupo": 30,
   "alt": "130"
 },
 {
   "id": "410e",
   "grupo": 30,
   "alt": "130e"
 },
 {
   "id": "410",
   "grupo": 30,
   "alt": "130y"
 },
 {
   "id": "B31N",
   "grupo": 31,
   "alt": "B31N"
 },
 {
   "id": "J11",
   "grupo": 31,
   "alt": "531"
 },
 {
   "id": "431c",
   "grupo": 31,
   "alt": "431c"
 },
 {
   "id": "431v",
   "grupo": 31,
   "alt": "431c2"
 },
 {
   "id": "G01",
   "grupo": 31,
   "alt": "231"
 },
 {
   "id": "G01c",
   "grupo": 31,
   "alt": "231c"
 },
 {
   "id": "J12",
   "grupo": 32,
   "alt": "532"
 },
 {
   "id": "H02",
   "grupo": 32,
   "alt": "332"
 },
 {
   "id": "G02",
   "grupo": 32,
   "alt": "232"
 },
 {
   "id": "J13",
   "grupo": 33,
   "alt": "533"
 },
 {
   "id": "J13c",
   "grupo": 33,
   "alt": "533c"
 },
 {
   "id": "H03",
   "grupo": 33,
   "alt": "333"
 },
 {
   "id": "G03",
   "grupo": 33,
   "alt": "233"
 },
 {
   "id": "J14c",
   "grupo": 34,
   "alt": "534c"
 },
 {
   "id": "H04",
   "grupo": 34,
   "alt": "334"
 },
 {
   "id": "G04",
   "grupo": 34,
   "alt": "234"
 },
 {
   "id": "J15c",
   "grupo": 35,
   "alt": "535c"
 },
 {
   "id": "435",
   "grupo": 35,
   "alt": "435"
 },
 {
   "id": "H05",
   "grupo": 35,
   "alt": "335"
 },
 {
   "id": "H05c",
   "grupo": 35,
   "alt": "335c"
 },
 {
   "id": "G05",
   "grupo": 35,
   "alt": "235"
 },
 {
   "id": "J16",
   "grupo": 36,
   "alt": "536"
 },
 {
   "id": "H06",
   "grupo": 36,
   "alt": "336"
 },
 {
   "id": "J17",
   "grupo": 37,
   "alt": "537"
 },
 {
   "id": "H07",
   "grupo": 37,
   "alt": "337"
 },
 {
   "id": "G07",
   "grupo": 37,
   "alt": "237"
 },
 {
   "id": "J18",
   "grupo": 38,
   "alt": "538"
 },
 {
   "id": "J18c",
   "grupo": 38,
   "alt": "538c"
 },
 {
   "id": "J18c",
   "grupo": 38,
   "alt": "538cy"
 },
 {
   "id": "H08",
   "grupo": 38,
   "alt": "338"
 },
 {
   "id": "G08",
   "grupo": 38,
   "alt": "238"
 },
 {
   "id": "G08v",
   "grupo": 38,
   "alt": "238e"
 },
 {
   "id": "J19",
   "grupo": 39,
   "alt": "539"
 },
 {
   "id": "J19c",
   "grupo": 39,
   "alt": "539c"
 },
 {
   "id": "H09",
   "grupo": 39,
   "alt": "339"
 },
 {
   "id": "G09",
   "grupo": 39,
   "alt": "239"
 },
 {
   "id": "J20",
   "grupo": 40,
   "alt": "540"
 },
 {
   "id": "D01",
   "grupo": 41,
   "alt": "441"
 },
 {
   "id": "G11",
   "grupo": 41,
   "alt": "241"
 },
 {
   "id": "D02",
   "grupo": 42,
   "alt": "442"
 },
 {
   "id": "H12",
   "grupo": 42,
   "alt": "342"
 },
 {
   "id": "G12",
   "grupo": 42,
   "alt": "242"
 },
 {
   "id": "D03",
   "grupo": 43,
   "alt": "443"
 },
 {
   "id": "H13",
   "grupo": 43,
   "alt": "343"
 },
 {
   "id": "G13",
   "grupo": 43,
   "alt": "243"
 },
 {
   "id": "H14",
   "grupo": 44,
   "alt": "344"
 },
 {
   "id": "G14",
   "grupo": 44,
   "alt": "244"
 },
 {
   "id": "D05",
   "grupo": 45,
   "alt": "445"
 },
 {
   "id": "345",
   "grupo": 45,
   "alt": "345"
 },
 {
   "id": "G15",
   "grupo": 45,
   "alt": "245"
 },
 {
   "id": "D06",
   "grupo": 46,
   "alt": "446"
 },
 {
   "id": "346N",
   "grupo": 46,
   "alt": "346N"
 },
 {
   "id": "G16",
   "grupo": 46,
   "alt": "246"
 },
 {
   "id": "D07",
   "grupo": 47,
   "alt": "447"
 },
 {
   "id": "D07c",
   "grupo": 47,
   "alt": "447c"
 },
 {
   "id": "D08",
   "grupo": 48,
   "alt": "448"
 },
 {
   "id": "D08c",
   "grupo": 48,
   "alt": "448c"
 },
 {
   "id": "348",
   "grupo": 48,
   "alt": "348"
 },
 {
   "id": "G18",
   "grupo": 48,
   "alt": "248"
 },
 {
   "id": "D09",
   "grupo": 49,
   "alt": "449"
 },
 {
   "id": "E18",
   "grupo": 49,
   "alt": "349"
 },
 {
   "id": "D10",
   "grupo": 50,
   "alt": "450"
 },
 {
   "id": "350",
   "grupo": 50,
   "alt": "350"
 },
 {
   "id": "350c",
   "grupo": 50,
   "alt": "350c"
 },
 {
   "id": "C01",
   "grupo": 51,
   "alt": "B51"
 },
 {
   "id": "C01c",
   "grupo": 51,
   "alt": "B51c"
 },
 {
   "id": "D11",
   "grupo": 51,
   "alt": "451"
 },
 {
   "id": "E01",
   "grupo": 51,
   "alt": "351"
 },
 {
   "id": "C02",
   "grupo": 52,
   "alt": "B52"
 },
 {
   "id": "C02c",
   "grupo": 52,
   "alt": "B52c"
 },
 {
   "id": "D12",
   "grupo": 52,
   "alt": "452"
 },
 {
   "id": "E02",
   "grupo": 52,
   "alt": "352"
 },
 {
   "id": "G22",
   "grupo": 52,
   "alt": "252"
 },
 {
   "id": "G22",
   "grupo": 52,
   "alt": "252y"
 },
 {
   "id": "C03",
   "grupo": 53,
   "alt": "B53"
 },
 {
   "id": "D13",
   "grupo": 53,
   "alt": "453"
 },
 {
   "id": "E03",
   "grupo": 53,
   "alt": "353"
 },
 {
   "id": "C03c",
   "grupo": 53,
   "alt": "B53c"
 },
 {
   "id": "213e",
   "grupo": 53,
   "alt": "F53e"
 },
 {
   "id": "C04",
   "grupo": 54,
   "alt": "B54"
 },
 {
   "id": "D14",
   "grupo": 54,
   "alt": "454"
 },
 {
   "id": "E04",
   "grupo": 54,
   "alt": "354"
 },
 {
   "id": "C05",
   "grupo": 55,
   "alt": "B55"
 },
 {
   "id": "D15",
   "grupo": 55,
   "alt": "455"
 },
 {
   "id": "E05",
   "grupo": 55,
   "alt": "355"
 },
 {
   "id": "C06",
   "grupo": 56,
   "alt": "B56"
 },
 {
   "id": "D16",
   "grupo": 56,
   "alt": "456"
 },
 {
   "id": "E06",
   "grupo": 56,
   "alt": "356"
 },
 {
   "id": "C07",
   "grupo": 57,
   "alt": "B57"
 },
 {
   "id": "D17",
   "grupo": 57,
   "alt": "457"
 },
 {
   "id": "E07",
   "grupo": 57,
   "alt": "357"
 },
 {
   "id": "C08",
   "grupo": 58,
   "alt": "B58"
 },
 {
   "id": "D18",
   "grupo": 58,
   "alt": "458"
 },
 {
   "id": "E08",
   "grupo": 58,
   "alt": "358"
 },
 {
   "id": "C09",
   "grupo": 59,
   "alt": "B59"
 },
 {
   "id": "E09",
   "grupo": 59,
   "alt": "359"
 },
 {
   "id": "C10e",
   "grupo": 60,
   "alt": "B60e"
 },
 {
   "id": "D20",
   "grupo": 60,
   "alt": "460"
 },
 {
   "id": "E10",
   "grupo": 60,
   "alt": "360"
 },
 {
   "id": "C11",
   "grupo": 61,
   "alt": "B61"
 },
 {
   "id": "C11c",
   "grupo": 61,
   "alt": "B61c"
 },
 {
   "id": "E11",
   "grupo": 61,
   "alt": "361"
 },
 {
   "id": "261e",
   "grupo": 61,
   "alt": "261e"
 },
 {
   "id": "C12",
   "grupo": 62,
   "alt": "B62"
 },
 {
   "id": "E12",
   "grupo": 62,
   "alt": "362"
 },
 {
   "id": "C13",
   "grupo": 63,
   "alt": "B63"
 },
 {
   "id": "E13",
   "grupo": 63,
   "alt": "363"
 },
 {
   "id": "C14",
   "grupo": 64,
   "alt": "B64"
 },
 {
   "id": "E14",
   "grupo": 64,
   "alt": "364"
 },
 {
   "id": "C15",
   "grupo": 65,
   "alt": "B65"
 },
 {
   "id": "E15",
   "grupo": 65,
   "alt": "365"
 },
 {
   "id": "E15c",
   "grupo": 65,
   "alt": "365c"
 },
 {
   "id": "C16",
   "grupo": 66,
   "alt": "B66"
 },
 {
   "id": "E16",
   "grupo": 66,
   "alt": "366"
 },
 {
   "id": "C17",
   "grupo": 67,
   "alt": "B67"
 },
 {
   "id": "E17",
   "grupo": 67,
   "alt": "367"
 },
 {
   "id": "C18",
   "grupo": 68,
   "alt": "B68"
 },
 {
   "id": "I21",
   "grupo": 68,
   "alt": "368"
 },
 {
   "id": "C19",
   "grupo": 69,
   "alt": "B69"
 },
 {
   "id": "I22",
   "grupo": 69,
   "alt": "369"
 },
 {
   "id": "C20",
   "grupo": 70,
   "alt": "B70"
 },
 {
   "id": "C21N",
   "grupo": 71,
   "alt": "B71"
 },
 {
   "id": "I01",
   "grupo": 71,
   "alt": "371"
 },
 {
   "id": "C22",
   "grupo": 72,
   "alt": "B72"
 },
 {
   "id": "I02",
   "grupo": 72,
   "alt": "372"
 },
 {
   "id": "C23",
   "grupo": 73,
   "alt": "B73"
 },
 {
   "id": "I03",
   "grupo": 73,
   "alt": "373"
 },
 {
   "id": "I03c",
   "grupo": 73,
   "alt": "373c"
 },
 {
   "id": "I04",
   "grupo": 74,
   "alt": "374"
 },
 {
   "id": "I04c",
   "grupo": 74,
   "alt": "374c"
 },
 {
   "id": "I04e",
   "grupo": 74,
   "alt": "374e"
 },
 {
   "id": "I05",
   "grupo": 75,
   "alt": "375"
 },
 {
   "id": "I07",
   "grupo": 77,
   "alt": "377"
 },
 {
   "id": "I08",
   "grupo": 78,
   "alt": "378"
 },
 {
   "id": "I08N",
   "grupo": 78,
   "alt": "378N"
 },
 {
   "id": "I08c",
   "grupo": 78,
   "alt": "378c"
 },
 {
   "id": "I09",
   "grupo": 79,
   "alt": "379"
 },
 {
   "id": "I09c",
   "grupo": 79,
   "alt": "379c"
 },
 {
   "id": "I09v",
   "grupo": 79,
   "alt": "379c2"
 },
 {
   "id": "I09v",
   "grupo": 79,
   "alt": "379c2y"
 },
 {
   "id": "I09N",
   "grupo": 79,
   "alt": "379c3"
 },
 {
   "id": "I09e",
   "grupo": 79,
   "alt": "379e"
 },
 {
   "id": "I10",
   "grupo": 80,
   "alt": "380"
 },
 {
   "id": "I10N",
   "grupo": 80,
   "alt": "380N"
 },
 {
   "id": "I11",
   "grupo": 81,
   "alt": "381"
 },
 {
   "id": "I12",
   "grupo": 82,
   "alt": "382"
 },
 {
   "id": "I13",
   "grupo": 83,
   "alt": "383"
 },
 {
   "id": "I14",
   "grupo": 84,
   "alt": "384"
 },
 {
   "id": "385",
   "grupo": 85,
   "alt": "385"
 },
 {
   "id": "I16",
   "grupo": 86,
   "alt": "386"
 },
 {
   "id": "I17",
   "grupo": 87,
   "alt": "387"
 },
 {
   "id": "I18",
   "grupo": 88,
   "alt": "388"
 },
 {
   "id": "I20",
   "grupo": 89,
   "alt": "389"
 }
 ]

    var lista = [];
    var i;
    var j;
    var error = false
    for(i = 0; i<$scope.items.length; i++) {
        if($scope.items[i]["id"] == $scope.data.note) {
            var hola = $scope.items[i]["grupo"];
            for(j = 0; j<$scope.items.length; j++) {
                if(($scope.items[j]["grupo"] == hola) && ($scope.items[j]["id"] != $scope.data.note) ) {
                   lista.push($scope.items[j]["id"]);
                   console.log(lista)
                } 
            }
            
            break;
        } 
        }
        console.log(i)
        
        if(i < $scope.items.length){
        lista.push($scope.items[i]["alt"])
} else{
    error = true;
}
       console.log(error)
       
$scope.mistake = {
    "malo" : error
};

$scope.lamicro = {
    "microscom" : lista
};

}])
   
.controller('paraderoCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams) {


}])
   
.controller('thanksCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams) {


}])
 