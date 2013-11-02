#!/usr/bin/perl -w

use JSON;

my $SRC_FILE = shift(@ARGV) or die("Usage $0 [file] [section]\n");
my $SECTION  = shift(@ARGV) or die("Usage $0 [file] [section]\n");

my %SECTION_FUNC = (
    PersonalInfo        => \&getPersonalInfo,
    ContactInfo         => \&getContactInfo,
    EmergencyContacts   => \&getEmergency,
    Reminders           => \&getReminders,
    Appointments        => \&getAppointments,
    Allergies           => \&getAllergies
    );

my %SECTION_MAP     = (
    AccountInfo         => "MY HEALTHEVET ACCOUNT SUMMARY",
    PersonalInfo        => "DEMOGRAPHICS",
    ContactInfo         => "DEMOGRAPHICS",
    EmergencyContacts   => "DEMOGRAPHICS",
    Reminders           => "VA WELLNESS REMINDERS",
    Appointments        => "VA APPOINTMENTS",
    Allergies           => "VA ALLERGIES"
#   => HEALTH CARE PROVIDERS
#   => TREATMENT FACILITIES
#   => HEALTH INSURANCE
#   => VA WELLNESS REMINDERS
#   => VA APPOINTMENTS
#   => VA MEDICATION HISTORY
#   => MEDICATIONS AND SUPPLEMENTS
#   => VA ALLERGIES
#   => ALLERGIES/ADVERSE REACTIONS
#   => MEDICAL EVENTS
#   => IMMUNIZATIONS
#   => VA LABORATORY RESULTS
#   => LABS AND TESTS
#   => VITALS AND READINGS
#   => FAMILY HEALTH HISTORY
#   => MILITARY HEALTH HISTORY
#   => DOD MILITARY SERVICE INFORMATION
    );

open(FH, "<", $SRC_FILE) or die("Unable to open file \"${ SRC_FILE }\": $!\n");
my $section_key = $SECTION_MAP{$SECTION};
my $obj_ref = undef;
LINE: while (my $line = <FH>)
    {
    chomp($line);
    next LINE unless ($line =~ /^-{5,}?\s+$section_key\s+-{5,}$/);
    my $txt_ref = getSection($1, *FH);

    $obj_ref = $SECTION_FUNC{$SECTION}->($txt_ref);
    }
close(FH);

my $json = JSON->new;
my $output = $json->encode($obj_ref);

print $output;

#foreach my $itm (@{ $obj_ref })
#    {
#    print "ITEM:[$itm]\n";
#    foreach my $key (sort keys %{ $itm })
#        {
#        print "\t[$key]::[$itm->{$key}]\n";
#        }
#    print "\n";
#    }

#foreach my $key (sort keys %{ $obj_ref })
#   {
#   print "[$key]::[$obj_ref->{$key}]\n";
#   }

sub trim
    {
    my $str = shift(@_);

    $str =~ s/^\s+//g;
    $str =~ s/\s+$//g;

    return $str
    }

sub getSection
    {
    my ($section, $fh) = @_;

    my @lines = ();
    SECTION_LINE: while (my $sline = <$fh>)
        {
        chomp($sline);
        last SECTION_LINE if ($sline =~ /^-{5,}?\s+[A-Z0-9 \/-]+\s+-{5,}$/o);
        push(@lines, $sline);
        }

    return \@lines;
    }

sub getPersonalInfo
    {
    my $txt_ref = shift(@_);

    my %obj = ();
    INFO_LINE: foreach my $ln (@{ $txt_ref })
        {
        next INFO_LINE unless ($ln);
        last INFO_LINE if ($ln =~ /^Mailing or Destination Address/o);

        if ($ln =~ /^Source:\s+(.+)\s*$/)
            {
            $obj{'Source'} = $1;
            }
        if ($ln =~ /^First Name:\s+(.*?)\s*$/)
            {
            $obj{'FirstName'} = $1;
            }
        if ($ln =~ /^Middle Initial:\s+(.*?)\s*$/)
            {
            $obj{'MiddleInitial'} = $1;
            }
        if ($ln =~ /^Last Name:\s+(.*?)\s*$/)
            {
            $obj{'LastName'} = $1;
            }
        if ($ln =~ /^Suffix:\s+(.*?)\s*$/)
            {
            $obj{'Suffix'} = $1;
            }
        if ($ln =~ /^Alias:\s+(.*?)\s*$/)
            {
            $obj{'Alias'} = $1;
            }
        if ($ln =~ /^Relationship to VA:\s+(.*?)\s*$/)
            {
            $obj{'Relationship'} = $1;
            }
        if ($ln =~ /^Gender:\s+(\w*?)\s+Blood Type:\s+([\w+-]*?)\s+Organ Donor:\s+(Yes|No)?\s*$/)
            {
            $obj{'Gender'} = $1;
            $obj{'BloodType'} = $2;
            $obj{'OrganDonor'} = $3;
            }
        if ($ln =~ /^Date of Birth:\s+(.*?)\s*$/)
            {
            $obj{'DateOfBirth'} = $1;
            }
        if ($ln =~ /^Marital Status:\s+(.*?)\s*$/)
            {
            $obj{'MaritalStatus'} = $1;
            }
        if ($ln =~ /^Current Occupation:\s+(.*?)\s*$/)
            {
            $obj{'CurrentOccupation'} = $1;
            }
        }

    return \%obj;
    }

sub getContactInfo
    {
    my $txt_ref = shift(@_);

    my %obj = ();
    my $start = undef;
    INFO_LINE: foreach my $ln (@{ $txt_ref })
        {
        if ($start || ($ln =~ /^Mailing or Destination Address/o))
            {
            $start = 1;
            }
        next INFO_LINE unless ($ln);
        next INFO_LINE unless ($start);
        last INFO_LINE if ($ln =~ /^EMERGENCY CONTACTS/o);

        if ($ln =~ /^Mailing or Destination Address:\s+(.*?)\s*$/)
            {
            $obj{'Address1'} = $1;
            }
        if ($ln =~ /^Mailing or Destination Address2:\s+(.*?)\s*$/)
            {
            $obj{'Address2'} = $1;
            }
        if ($ln =~ /^Mailing or Destination City:\s+(.*?)\s*$/)
            {
            $obj{'City'} = $1;
            }
        if ($ln =~ /^Mailing or Destination State:\s+(.*?)\s*$/)
            {
            $obj{'State'} = $1;
            }
        if ($ln =~ /^Mailing or Destination Country:\s+(.*?)\s*$/)
            {
            $obj{'Country'} = $1;
            }
        if ($ln =~ /^Mailing or Destination Province:\s+(.*?)\s*$/)
            {
            $obj{'Province'} = $1;
            }
        if ($ln =~ /^Mailing or Destination Zip\/Postal Code:\s+(.*?)\s*$/)
            {
            $obj{'PostalCode'} = $1;
            }
        if ($ln =~ /^Home Phone Number:\s+(.*?)\s*$/)
            {
            $obj{'HomePhoneNumber'} = $1;
            }
        if ($ln =~ /^Work Phone Number:\s+(.*?)\s*$/)
            {
            $obj{'WorkPhoneNumber'} = $1;
            }
        if ($ln =~ /^Pager Number:\s+(.*?)\s*$/)
            {
            $obj{'PagerNumber'} = $1;
            }
        if ($ln =~ /^Cell Phone Number:\s+(.*?)\s*$/)
            {
            $obj{'CellPhoneNumber'} = $1;
            }
        if ($ln =~ /^FAX Number:\s+(.*?)\s*$/)
            {
            $obj{'FaxNumber'} = $1;
            }
        if ($ln =~ /^Email Address:\s+(.*?)\s*$/)
            {
            $obj{'EmailAddress'} = $1;
            }
        if ($ln =~ /^Preferred Method of Contact:\s+(.*?)\s*$/)
            {
            $obj{'PreferredMethod'} = $1;
            }
        }

    return \%obj;
    }

sub getEmergency
    {
    my $txt_ref = shift(@_);

    my %obj  = ();
    my @list = ();
    my $start = undef;
    my $new   = 0;
    INFO_LINE: foreach my $ln (@{ $txt_ref })
        {
        if ($start || ($ln =~ /^EMERGENCY CONTACTS/o))
            {
            $start = 1;
            }
        next INFO_LINE unless ($start);

        if ($ln eq "")
            {
            my %new_obj = ();
            if ($new > 0)
                {
                foreach my $key (keys %obj)
                    {
                    $new_obj{$key} = $obj{$key};
                    }
                push(@list, \%new_obj);
                }
            %obj = ();
            $new++;

            next INFO_LINE;
            }

        if ($ln =~ /^Contact First Name:\s+(.*?)\s*$/)
            {
            $obj{'FirstName'} = $1;
            }
        if ($ln =~ /^Contact Last Name:\s+(.*?)\s*$/)
            {
            $obj{'LastName'} = $1;
            }
        if ($ln =~ /^Relationship:\s+(.*?)\s*$/)
            {
            $obj{'Relationship'} = $1;
            }
        if ($ln =~ /^Home Phone Number:\s+(.*?)\s*$/)
            {
            $obj{'HomePhoneNumber'} = $1;
            }
        if ($ln =~ /^Work Phone Number:\s+(.*?)\s*Extension:\s+(.*?)\s*$/)
            {
            $obj{'WorkPhoneNumber'} = $1;
            $obj{'WorkPhoneExtension'} = $2;
            }
        if ($ln =~ /^Cell Phone Number:\s+(.*?)\s*$/)
            {
            $obj{'CellPhoneNumber'} = $1;
            }
        if ($ln =~ /^Address Line 1:\s+(.*?)\s*$/)
            {
            $obj{'Address1'} = $1;
            }
        if ($ln =~ /^Address Line 2:\s+(.*?)\s*$/)
            {
            $obj{'Address2'} = $1;
            }
        if ($ln =~ /^City:\s+(.*?)\s*$/)
            {
            $obj{'City'} = $1;
            }
        if ($ln =~ /^State:\s+(.*?)\s*$/)
            {
            $obj{'State'} = $1;
            }
        if ($ln =~ /^Country:\s+(.*?)\s*$/)
            {
            $obj{'Country'} = $1;
            }
        if ($ln =~ /^Province:\s+(.*?)\s*$/)
            {
            $obj{'Province'} = $1;
            }
        if ($ln =~ /^Zip\/Post Code:\s+(.*?)\s*$/)
            {
            $obj{'PostalCode'} = $1;
            }
        if ($ln =~ /^Email Address:\s+(.*?)\s*$/)
            {
            $obj{'EmailAddress'} = $1;
            }
        }

    return \@list;
    }

sub getReminders
    {
    my $txt_ref = shift(@_);

    my @list = ();
    my $start = undef;
    my $source = undef;
    my $lastup = undef;
    INFO_LINE: foreach my $ln (@{ $txt_ref })
        {
        if ($start || ($ln =~ /^-{5,}\s*$/o))
            {
            $start = 1;
            }
        else
            {
            if ($ln =~ /^Source:\s+(.*?)\s*$/)
                {
                $source = $1;
                }
            if ($ln =~ /^Last Updated:\s+(.*?)\s*$/)
                {
                $lastup = $1;
                }
            }

        next INFO_LINE unless ($start);
        last INFO_LINE unless ($ln);

        my %obj = ();
        if ($ln =~ /^(.{35})(.{12})(.{17})(.{12})$/)
            {
            $obj{'Source'}              = $source;
            $obj{'LastUpdated'}         = $lastup;
            $obj{'ReminderDescription'} = trim($1);
            $obj{'DueDate'}             = trim($2);
            $obj{'LastCompleted'}       = trim($3);
            $obj{'Location'}            = trim($1);

            push(@list, \%obj);
            }
        }

    return \@list;
    }

sub getAppointments
    {
    my $txt_ref = shift(@_);

    my @list    = ();
    my %obj     = ();
    my $start   = undef;
    my $source  = undef;
    my $lastup  = undef;
    my $note    = undef;
    my $new     = 0;
    INFO_LINE: foreach my $ln (@{ $txt_ref })
        {
        if ($start || ($ln =~ /^-{5,}\s*$/o))
            {
            $start = 1;
            }
        else
            {
            if ($ln =~ /^Source:\s+(.*?)\s*$/)
                {
                $source = $1;
                }
            if ($ln =~ /^Last Updated:\s+(.*?)\s*$/)
                {
                $lastup = $1;
                }
            }

        next INFO_LINE unless ($start);
        next INFO_LINE unless ($ln);

        if ($ln =~ /^Date\/Time:\s+(.*?)\s*$/)
            {
            my $datetime = $1;
            if ($new > 0)
                {
                my %new_obj = ();
                foreach my $key (keys %obj)
                    {
                    $new_obj{$key} = $obj{$key};
                    }
                push(@list, \%new_obj);
                }
            %obj = ();
            $obj{'DateTime'}    = $datetime;
            $obj{'Source'}      = $source;
            $obj{'LastUpdated'} = $lastup;
            $new++;
            $note = undef;

            next INFO_LINE;
            }

        if ($note)
            {
            $obj{'Note'} .= "\n${ ln }";
            }
        else
            {
            if ($ln =~ /^Location:\s+(.*?)\s*$/)
                {
                $obj{'Location'} = $1;
                }
            if ($ln =~ /^Status:\s+(.*?)\s*$/)
                {
                $obj{'Status'} = $1;
                }
            if ($ln =~ /^Clinic:\s+(.*?)\s*$/)
                {
                $obj{'Clinic'} = $1;
                }
            if ($ln =~ /^Phone Number:\s+(.*?)\s*$/)
                {
                $obj{'Phone Number'} = $1;
                }
            if ($ln =~ /^Type:\s+(.*?)\s*$/)
                {
                $obj{'Type'} = $1;
                }
            if ($ln =~ /^Note:\s+(.*?)\s*$/)
                {
                $obj{'Note'} = $1;
                $note = 1;
                }
            }
        }

    return \@list;
    }

sub getAllergies
    {
    my $txt_ref = shift(@_);

    my %obj     = ();
    my @list    = ();
    my $start   = undef;
    my $new     = 0;
    my $source  = undef;
    my $lastup  = undef;
    my $comment = undef;
    INFO_LINE: foreach my $ln (@{ $txt_ref })
        {
        unless ($start)
            {
            if ($ln =~ /^Source:\s+(.*?)\s*$/)
                {
                $source = $1;
                }
            if ($ln =~ /^Last Updated:\s+(.*?)\s*$/)
                {
                $lastup = $1;
                $start = 1;
                }
            }
        next INFO_LINE unless ($start);
        last INFO_LINE if ($ln =~ /^Please contact your health care team/o);

        if ($ln eq "")
            {
            if ($new > 0)
                {
                my %new_obj = ();
                if (%obj)
                    {
                    $new_obj{'Source'} = $source;
                    $new_obj{'LastUpdated'} = $lastup;
                    foreach my $key (keys %obj)
                        {
                        $new_obj{$key} = $obj{$key};
                        }
                    push(@list, \%new_obj);
                    }
                }
            %obj = ();
            $new++;
            $comment = undef;

            next INFO_LINE;
            }

        if ($comment)
            {
            $obj{'Comments'} .= "\n${ ln }";
            }
        else
            {
            if ($ln =~ /^Allergy Name:\s+(.*?)\s*$/)
                {
                $obj{'AllergyName'} = $1;
                }
            if ($ln =~ /^Location:\s+(.*?)\s*$/)
                {
                $obj{'Location'} = $1;
                }
            if ($ln =~ /^Date Entered:\s+(.*?)\s*$/)
                {
                $obj{'DateEntered'} = $1;
                }
            if ($ln =~ /^Reaction:\s+(.*?)\s*$/)
                {
                $obj{'Reaction'} = $1;
                }
            if ($ln =~ /^Allergy Type:\s+(.*?)\s*$/)
                {
                $obj{'AllergyType'} = $1;
                }
            if ($ln =~ /^VA Drug Class:\s+(.*?)\s*$/)
                {
                $obj{'DrugClass'} = $1;
                }
            if ($ln =~ /^Observed\/Historical:\s+(.*?)\s*$/)
                {
                $obj{'ObservedOrHistorical'} = $1;
                }
            if ($ln =~ /^Comments:\s+(.*?)\s*$/)
                {
                $obj{'Comments'} = $1;
                $comment = 1;
                }
            }
        }

    return \@list;
    }
