$(function()
    {
        $("#signUpForm").submit(function()
        {
            $.ajax({
                data: $(this).serialize(),
                url: this.action,
                timeout: 2000,
                error:function(obj)
                {
                    $("#error-placeholder").append(obj);
                },
                success: function(nextPageUrl)
                {
                    window.location.replace(nextPageUrl);
                }
            });
            return false;
        });
    });