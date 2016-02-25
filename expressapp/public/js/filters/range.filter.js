angular.module('frameApp').filter("range", function() {	
    return function(input) {
            var lowBound, highBound, incrementBy;
            switch (input.length) {
            case 1:
                lowBound = 0;
                highBound = parseInt(input[0]) - 1;
                incrementBy = 1;
                break;
            case 2:
                lowBound = parseInt(input[0]);
                highBound = parseInt(input[1]);
                incrementBy = 1;
                break;
            case 3:
                lowBound = parseInt(input[0]);
                highBound = parseInt(input[1]);
                incrementBy = parseInt(input[2]);
                break;  
            default:
                return input;
            }
            var result = [];
            for (var i = lowBound; i <= highBound; i = i + incrementBy)
                result.push(i);
            return result;
    };
});